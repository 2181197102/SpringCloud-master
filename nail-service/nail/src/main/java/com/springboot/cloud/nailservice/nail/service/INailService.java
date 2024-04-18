package com.springboot.cloud.nailservice.nail.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.cloud.nailservice.nail.entity.form.SaveDiagForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.vo.NailDiagVo;

import java.util.List;

public interface INailService {
    boolean saveDiag(SaveDiagForm saveDiagForm);

    boolean delete(String diagnosisCode);

    boolean update(NailDiag nailDiag);

    NailDiagVo get(String diagnosisCode);

    List<NailDiag> getAll();

    IPage<NailDiag> query(Page page, NailDiagQueryParam toParam);

    void saveOneDiag(NailDiag save);
}
