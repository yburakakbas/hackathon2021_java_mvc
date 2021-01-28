package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Favorite;
import tr.itelligence.hackathon.model.FavoriteDTO;
import tr.itelligence.hackathon.model.PaginationResult;

import java.util.List;

public interface FavoriteDAO {

    public void save(Favorite favorite);

    public Favorite getByUserNameAndProductCode(String userName, String productCode);

    public PaginationResult<FavoriteDTO> getAllByUserName(String userName, int page, int maxResult, int maxNavigationPage);

}

