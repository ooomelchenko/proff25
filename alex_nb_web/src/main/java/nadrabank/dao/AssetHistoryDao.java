package nadrabank.dao;

import nadrabank.domain.AssetHistory;

public interface AssetHistoryDao {
    Long create( AssetHistory  assetHistory);
    AssetHistory read(Long id);
    boolean update( AssetHistory assetHistory);
    boolean delete( AssetHistory assetHistory);
}