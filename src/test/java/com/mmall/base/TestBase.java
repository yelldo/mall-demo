package com.mmall.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单元测试基类
 *
 * 注解说明：
 *
 * <p>
 * @RunWith: 用于指定junit运行环境，是junit提供给其他框架测试环境接口扩展，spring
 * 为了便于使用spring的依赖注入，spring提供了org.springframework.test.context.junit4.SpringJUnit4ClassRunner作为junit测试环境。
 * @ContextConfiguration:用于指定spring配置环境。
 * @TransactionConfiguration:用于配置事物。
 * @Transactional:表示所有类下所有方法都使用事物。
 * </p>
 *
 * Created by luzy on 2017/12/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public abstract class TestBase {
//public abstract class TestBase extends AbstractTransactionalJUnit4SpringContextTests {

}
