package com.springboot.cloud.nailservice.nail.service;

import jdk.jshell.Diag;

public interface INailService {
    Diag getDiagById(String id);
    List<Diag> getDiagsByUserId(String userId);
    Diag createDiag(Diag diag);
    Diag updateDiag(String id, Diag diag);
    void deleteDiag(String id);
}
