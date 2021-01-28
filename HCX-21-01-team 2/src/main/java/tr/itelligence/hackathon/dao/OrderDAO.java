package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.model.CartInfo;
import tr.itelligence.hackathon.model.OrderDetailInfo;
import tr.itelligence.hackathon.model.OrderInfo;
import tr.itelligence.hackathon.model.PaginationResult;

import java.util.List;


public interface OrderDAO {

    public void saveOrder(CartInfo cartInfo);

    public PaginationResult<OrderInfo> listOrderInfo(int page,
                                                     int maxResult, int maxNavigationPage);

    public OrderInfo getOrderInfo(String orderId);

    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);

    public List<OrderDetailInfo> getAllByProductId(String productCode);

}