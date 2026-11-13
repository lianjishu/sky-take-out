package io.github.lianjishu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.github.lianjishu.annotation.AutoFill;
import io.github.lianjishu.dto.DishDTO;
import io.github.lianjishu.dto.DishPageQueryDTO;
import io.github.lianjishu.entity.Dish;
import io.github.lianjishu.entity.DishFlavor;
import io.github.lianjishu.enumeration.OperationType;
import io.github.lianjishu.mapper.DishFlavorMapper;
import io.github.lianjishu.mapper.DishMapper;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.service.DishService;
import io.github.lianjishu.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;

    @Autowired
    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
    }

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品信息
     */
    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.insert(dish);
        Long dishId = dish.getId();

        if (dishDTO.getFlavors() != null && !dishDTO.getFlavors().isEmpty()) {
            dishDTO.getFlavors().forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(dishDTO.getFlavors());
        }

    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO 菜品分页查询参数
     * @return 菜品分页查询结果
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        List<DishVO> dishes = dishMapper.selectPage(dishPageQueryDTO);
        Page<DishVO> p = (Page<DishVO>) dishes; // Changed from Page<Dish> to Page<DishVO>
        log.info("分页查询菜品:{}", p.toString());

        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 根据id更新菜品
     *
     * @param dishDTO 菜品信息
     */
    @AutoFill(OperationType.UPDATE)
    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        // 先删除旧口味数据，再重新插入新口味数据
        dishFlavorMapper.deleteByDishIds(Arrays.asList(dishDTO.getId()));
        if (dishDTO.getFlavors() != null && !dishDTO.getFlavors().isEmpty()) {
            dishDTO.getFlavors().forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(dishDTO.getFlavors());
        }
    }

    /**
     * 根据id查询菜品
     *
     * @param dishId 菜品ID
     * @return 菜品信息VO
     */
    @Override
    public DishVO getByDishId(Long dishId) {
        DishVO dishVO = dishMapper.selectById(dishId);
        log.info("先查基本信息:{}", dishVO);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectById(dishId);
        log.info("再查口味信息:{}", dishFlavors);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 根据菜品ID删除菜品
     *
     * @param ids 菜品ID
     */
    @Transactional
    @Override
    public void deleteByIds(List<Long> ids) {
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /**
     *
     * @param categoryId
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        List<Dish> dish = dishMapper.selectByCategoryId(categoryId);
        return dish;
    }

    /**
     *
     * @param status
     */
    @Override
    public void startOrStop(Long id, Integer status) {
        dishMapper.updateStatus(id, status);
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.selectById(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

}
