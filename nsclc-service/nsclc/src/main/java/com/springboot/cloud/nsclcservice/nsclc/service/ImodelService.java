package com.springboot.cloud.nsclcservice.nsclc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.ModelForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.ModelQueryParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.PredictParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.PredictVo;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.List;

public interface ImodelService {
    PredictVo predict(PredictParam predictParam) throws TException, IOException;

    boolean saveModelInfo(ModelForm modelForm);

    boolean delete(String modelCode);

    boolean update(Model model);

    Model get(String modelCode);

    List<Model> getAll();

    IPage<Model> query(Page page, ModelQueryParam toParam);
}
