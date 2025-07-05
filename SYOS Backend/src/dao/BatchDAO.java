package dao;

import model.Batch;

public interface BatchDAO {
    void insertBatch(Batch batch, String itemCode);
}
