from py.thrift.generated import ttypes
import SimpleITK as sitk
from radiomics import featureextractor
from datetime import datetime
from sklearn.pipeline import Pipeline
import joblib
import numpy as np
import pandas as pd


class PredictServiceImpl:

    def extractFeatures(self, image_path, label_path, extractor, file_name):
        index = []
        patient_info = image_path.split('/')[-1].split('.')[0]
        index.append(patient_info)
        image = sitk.ReadImage(image_path)
        label = sitk.ReadImage(label_path)
        features = extractor.execute(image, label)
        features_dict = features.items()
        features_df = pd.DataFrame(list(features_dict)).T
        features_df.columns = features_df.iloc[0, :]
        _str_array = [s.replace('-', '') for s in features_df.columns]
        features_df.columns = [s.replace('_', '') for s in _str_array]
        features_df.drop(index=[0], inplace=True)
        result = pd.DataFrame(data=features_df.iloc[0:, 0:], columns=features_df.columns)
        result.index = [patient_info]
        formatted_time = datetime.now().strftime("%Y%m%d%H%M%S")
        xlsx_name = file_name + patient_info + '_' + formatted_time + '.xlsx'
        xlsx_obj = pd.ExcelWriter(xlsx_name)
        result.to_excel(xlsx_obj)
        xlsx_obj._save()
        return xlsx_name

    def executor(self, image_path, label_path):
        extractor = featureextractor.RadiomicsFeatureExtractor()
        extractor.enableImageTypeByName('LoG')
        extractor.enableImageTypeByName('Wavelet')
        extractor.enableAllFeatures()
        image_path = image_path
        label_path = label_path
        result = self.extractFeatures(image_path, label_path, extractor=extractor, file_name='/app/server/datas/files/')
        return result

    def predict(self, path, modelLoc):
        data = pd.read_excel(path, index_col=0)
        features_name = np.load('/app/server/datas/features_name.npy', allow_pickle=True)

        s = data[data.columns.intersection(features_name)]

        model = load_model(modelLoc)

        predict = model.predict(s)
        return int(predict)

    def predictNSCLC(self, image_path, label_path, modelLoc):
        feature_path = self.executor(image_path, label_path)
        result = self.predict(feature_path, modelLoc)
        return ttypes.PredictInfo(feature_path, result)



def load_model(location):
    # 加载模型
    loaded_model = joblib.load(location)
    return loaded_model
