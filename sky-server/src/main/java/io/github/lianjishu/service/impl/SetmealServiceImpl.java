package io.github.lianjishu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.github.lianjishu.dto.SetmealDTO;
import io.github.lianjishu.dto.SetmealPageQueryDTO;
import io.github.lianjishu.entity.Setmeal;
import io.github.lianjishu.entity.SetmealDish;
import io.github.lianjishu.mapper.SetmealDishMapper;
import io.github.lianjishu.mapper.SetmealMapper;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.service.SetmealService;
import io.github.lianjishu.vo.DishItemVO;
import io.github.lianjishu.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;

    @Autowired
    public SetmealServiceImpl(SetmealMapper setmealMapper, SetmealDishMapper setmealDishMapper) {
        this.setmealMapper = setmealMapper;
        this.setmealDishMapper = setmealDishMapper;
    }

    /**
     * 分页查询套餐列表
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<SetmealVO> setmealVOList = setmealMapper.pageQuery(setmealPageQueryDTO);
        Page<SetmealVO> page = (Page<SetmealVO>) setmealVOList;
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     *
     * @param setmealDTO 饮食套餐数据传输对象
     */
    @Transactional
    @Override
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.insert(setmeal);
        Long returnedSetmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(returnedSetmealId));

        setmealDishMapper.insert(setmealDishes);
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public SetmealVO queryById(Long id) {
        SetmealVO setmealVO = setmealMapper.selectById(id);
        if (setmealVO == null) {
            return null;
        }

        List<SetmealDish> setmealDishes = setmealDishMapper.selectBySetmealId(id);
        if (setmealDishes == null) {
            setmealDishes = new ArrayList<>();
        }
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐（同时"先删除后添加"关联的菜品）
     *
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //更新套餐主表信息
        setmealMapper.update(setmeal);

        //"先删除后添加"关联的菜品
        Long setmealId = setmealDTO.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
            setmealDishMapper.insert(setmealDishes);
        }
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

}
