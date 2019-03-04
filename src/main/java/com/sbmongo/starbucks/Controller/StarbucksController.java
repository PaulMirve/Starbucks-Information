package com.sbmongo.starbucks.Controller;

import com.sbmongo.starbucks.Constant.ViewConstant;
import com.sbmongo.starbucks.Repository.StarbucksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/starbucks")
public class StarbucksController {

    @Autowired
    @Qualifier("starbucksRepository")
    private StarbucksRepository starbucksRepository;

    @GetMapping("/info")
    public ModelAndView listInfo(){
        ModelAndView mav = new ModelAndView(ViewConstant.STARBUCKS);
        mav.addObject("starbucks", starbucksRepository.findAll());
        return mav;
    }
}
