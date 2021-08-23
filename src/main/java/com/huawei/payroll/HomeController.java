/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.payroll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page of the website
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
        if (AssessmentCache.initSuccess()) {
            return "index";
        } else {
            return "init";
        }
    }
}