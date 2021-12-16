package com.alibaba.csp.sentinel.demo.annotation.aop.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2021/12/13 下午7:04
 * @Modified By
 */
@Component
public class DemoStart {
    public static void main(String[] args) {
        // 配置规则.
        initFlowRules();

        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 exit entry
            System.out.println("循环");
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @PostConstruct
    public void test(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("22");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);


//        List<DegradeRule> rules2 = new ArrayList<>();
//        DegradeRule rule2 = new DegradeRule();
//        rule2.setResource("test");
//        // set threshold RT, 10 ms
//        rule2.setCount(1);
//        rule2.setGrade(RuleConstant.DEGRADE_GRADE_RT);
//        rule2.setTimeWindow(10);
//        rules2.add(rule2);
//        DegradeRuleManager.loadRules(rules2);
    }
}
