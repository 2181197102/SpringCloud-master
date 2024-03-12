# 甲病辅助诊断服务 API 文档

## 1. 概述

本 API 提供了一个甲病图像分类服务，用于分析和预测指甲图像的病变类型。它接受多张指甲图像作为输入，对每张图像进行分类预测，并给出每张图像最可能属于的前三个类别及其对应概率。同时，还提供了一个基于所有图片的预测结果的综合投票策略，以确定最可能的类别。

---

## 2. 接口描述

### 2.1 请求方法

- **URL**：`/predict`
- **方法**：`POST`

### 2.2 请求参数

- **file**：需要上传的图像文件，可以上传多个文件。每个文件应小于 5MB，且必须是有效的图像格式（".jpg" 格式）。

### 2.3 响应参数

- **200 OK**：请求成功。返回 JSON 对象，包含以下字段：
  - `Prediction time`：预测处理所需的时间（秒）。
  - `individual_predictions`：每张图片的前三个预测类别及其概率。
  - `most_likely_class`：所有图片的最可能类别。
- **400 Bad Request**：请求无效。可能是由于文件太大、格式不正确或未包含任何有效图像。

### 2.4 响应示例

成功响应示例：

```json
{
  "Prediction time": 2.345,
  "individual_predictions": [
    [
      ["Subungual Melanoma", "34.56%"],
      ["Onychomycosis", "28.91%"],
      ["Paronychia", "19.32%"]
    ],
    [
      ["Psoriatic Nails", "45.22%"],
      ["Nail Matrix Nevus", "25.88%"],
      ["Melanonychia", "14.67%"]
    ]
  ],
  "most_likely_class": "Subungual Melanoma"
}
```

错误响应示例：

```json
{
  "error": "No valid images provided"
}
```

---

## 3. 使用示例

使用 `curl` 发送 POST 请求：

```bash
curl -X POST \
  -F "file=@path_to_image1.jpg" \
  -F "file=@path_to_image2.jpg" \
  http://localhost:5000/predict
```

---

## 4. 注意事项

- 确保上传的文件是图像格式（".jpg" 格式），并且每个文件的大小不超过 5MB。
- 该 API 目前只支持对特定类型的指甲病进行分类。请确保上传的图像符合预期的使用场景。
- 本服务提供的结果仅供参考，不能作为医学诊断的依据。在采取任何医疗行动前，请咨询专业医生。

---

## 5. 安全和隐私

- 在处理敏感或私人图像时，请确保您的网络连接安全，并考虑图像的隐私性。
- 请不要将任何个人身份信息或敏感数据上传到 API。

---

