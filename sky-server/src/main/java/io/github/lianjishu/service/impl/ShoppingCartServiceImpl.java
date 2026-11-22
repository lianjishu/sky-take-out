package io.github.lianjishu.service.impl;

import io.github.lianjishu.context.BaseContext;
import io.github.lianjishu.dto.ShoppingCartDTO;
import io.github.lianjishu.entity.ShoppingCart;
import io.github.lianjishu.mapper.DishMapper;
import io.github.lianjishu.mapper.SetmealMapper;
import io.github.lianjishu.mapper.ShoppingCartMapper;
import io.github.lianjishu.service.ShoppingCartService;
import io.github.lianjishu.vo.DishVO;
import io.github.lianjishu.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper, DishMapper dishMapper, SetmealMapper setmealMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
    }

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        //只能查询自己的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());

        //判断当前商品是否在购物车中, 同一商品不多次展示， 仅仅改变数量
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

        if (shoppingCartList != null && shoppingCartList.size() == 1) {
            //如果已经存在，就更新数量，数量加1
            shoppingCart = shoppingCartList.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        } else {//如果不存在，插入数据，数量就是1
            //判断当前添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {//当前要加入购物车的是菜品
                DishVO dishVO = dishMapper.selectById(dishId);
                shoppingCart.setAmount(dishVO.getPrice());
                shoppingCart.setImage(dishVO.getImage());
                shoppingCart.setName(dishVO.getName());
            } else {//当前要加入购物车的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                SetmealVO setmealVO = setmealMapper.selectById(setmealId);
                shoppingCart.setAmount(setmealVO.getPrice());
                shoppingCart.setImage(setmealVO.getImage());
                shoppingCart.setName(setmealVO.getName());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 根据用户id或菜品id或套餐id查询购物车
     *
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(
                ShoppingCart.builder()
                        .userId(BaseContext.getCurrentId())
                        .build());
        return shoppingCartList;
    }

    /**
     * 清空购物车
     */
    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }

    /**
     * 减少购物车数量
     *
     * @param shoppingCartDTO
     */
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        // 设置查询条件，
        shoppingCart.setUserId(BaseContext.getCurrentId());
        // 查询当前登录用户的购物车数据
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {//当前登录用户的购物车数据不为空
            shoppingCart = shoppingCartList.get(0);
            Integer number = shoppingCart.getNumber();
            if (number == 1) {
                //当前商品在购物车中的份数为1，直接删除当前记录
                shoppingCartMapper.deleteById(shoppingCart.getId());
            } else {
                //当前商品在购物车中的份数不为1，修改份数即可
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }
        }
    }
}
