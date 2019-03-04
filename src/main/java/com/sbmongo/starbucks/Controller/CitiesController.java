package com.sbmongo.starbucks.Controller;

import com.sbmongo.starbucks.Constant.ViewConstant;
import com.sbmongo.starbucks.Entity.Cities;
import com.sbmongo.starbucks.Repository.CitiesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/starbucks")
public class CitiesController {

    @Autowired
    @Qualifier("citiesRepository")
    private CitiesRepository citiesRepository;

    @GetMapping("/cities")
    public ModelAndView listCities(){
        ModelAndView mav = new ModelAndView(ViewConstant.CITIES);
        mav.addObject("cities", citiesRepository.findAll());
        return mav;
    }

    @GetMapping("/searchCity")
    public ModelAndView searchCity(@RequestParam(name = "city", required = false)String city){
        ModelAndView mav = new ModelAndView(ViewConstant.CITIES);
        //System.out.println(citiesRepository.findByCity("Toronto"));
        mav.addObject("cities", citiesRepository.findByCity(city.toUpperCase()));
        return mav;
    }

    @GetMapping("/showchart")
    public ModelAndView showChart(Model model){
        ModelAndView mav = new ModelAndView(ViewConstant.CHART);
        //first, add the regional sales
        Integer northeastSales = 17089;
        Integer westSales = 10603;
        Integer midwestSales = 5223;
        Integer southSales = 10111;

        model.addAttribute("northeastSales", northeastSales);
        model.addAttribute("southSales", southSales);
        model.addAttribute("midwestSales", midwestSales);
        model.addAttribute("westSales", westSales);

        //now add sales by lure type
        List<Integer> inshoreSales = Arrays.asList(4074, 3455, 4112);
        List<Integer> nearshoreSales = Arrays.asList(3222, 3011, 3788);
        List<Integer> offshoreSales = Arrays.asList(7811, 7098, 6455);

        model.addAttribute("inshoreSales", inshoreSales);
        model.addAttribute("nearshoreSales", nearshoreSales);
        model.addAttribute("offshoreSales", offshoreSales);

        return mav;
    }
}
