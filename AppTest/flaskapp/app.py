from flask import Flask, request, jsonify
import torch
from torchvision import transforms
from PIL import Image
from vit_model_adapter import vit_base_patch16_224_in21k as create_model
import io
import numpy as np
import time

app = Flask(__name__)

# 定义类别名称
class_names = ['Psoriatic Nails', 'Paronychia', 'Nail Matrix Nevus', 'Subungual Melanoma', 'Melanonychia', ' Periungual Warts', 'Onychomycosis']

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
# 加载模型
model = create_model(num_classes=7, has_logits=False).to(device)
model_weight_path = "./model.pth"
model.load_state_dict(torch.load(model_weight_path, map_location=device),strict=False)
model.eval()

# 图像预处理
def transform_image(image_bytes):
    my_transforms = transforms.Compose([transforms.Resize(255),
                                        transforms.CenterCrop(224),
                                        transforms.ToTensor(),
                                        transforms.Normalize(
                                            [0.485, 0.456, 0.406],
                                            [0.229, 0.224, 0.225])])
    image = Image.open(io.BytesIO(image_bytes))
    image_tensor = my_transforms(image).unsqueeze(0)
    return image_tensor.to(device)  # 移动到相同的设备

def transform_images(image_bytes_list):
    return torch.cat([transform_image(image_bytes) for image_bytes in image_bytes_list], dim=0)

@app.route('/test', methods=['GET'])
def test_route():
    return "Test route is working!", 200


@app.route('/predict', methods=['POST'])
def predict():
    if request.method == 'POST':
        start_time = time.time()  # 开始计时

        image_files = request.files.getlist('file')  # 获取文件列表
        img_bytes_list = [file.read() for file in image_files]
        tensor = transform_images(img_bytes_list)
        with torch.no_grad():
            outputs = model(tensor)
        # 应用 Softmax 并将输出转换为概率
        probabilities = torch.nn.functional.softmax(outputs, dim=1).cpu().numpy()

        # 获取每张图片的前三个预测类别及其概率并按概率降序排列
        top3_class_names_probs = []
        for prob in probabilities:
            top3 = sorted(zip(class_names, prob), key=lambda x: x[1], reverse=True)[:3]
            top3 = [(name, f"{round(p * 100, 2)}%") for name, p in top3]  # 转换为 Python 原生 float 类型
            top3_class_names_probs.append(top3)

        # 投票策略确定最可能的类别
        all_preds = np.argsort(probabilities, axis=1)[:, -3:].flatten()
        most_likely_class_index = np.bincount(all_preds).argmax()
        most_likely_class = class_names[most_likely_class_index]

        end_time = time.time()  # 结束计时
        total_time = end_time - start_time
        print(f"Prediction time: {total_time:.4f} seconds.")

        return jsonify({'Prediction time': total_time,'individual_predictions': top3_class_names_probs, 'most_likely_class': most_likely_class})



if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
