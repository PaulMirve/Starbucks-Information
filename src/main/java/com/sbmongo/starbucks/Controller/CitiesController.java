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

import java.util.*;
import java.util.stream.Collectors;

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
        mav.addObject("cities", citiesRepository.findByCity(city.toUpperCase()));
        return mav;
    }

    @GetMapping("/pieChart")
    public String pieChart(Model model){
        List<Cities> citiesList = citiesRepository.findAll();
        //Takes only the element City of the MongoDB and puts them in a list called citiesNames
        List<String> citiesNames = citiesList.stream().map(Cities::getCity).collect(Collectors.toList());
        //Cleans the repeated cities
        Set<String> uniqueCities = new HashSet<>(citiesNames);
        citiesNames.clear();
        citiesNames.addAll(uniqueCities);
        model.addAttribute("listCities", citiesNames);
        /*For each city en citiesNames variable, the loop is going to send to the view the name of the cities and the count
        of how much cities there are in that city*/
        System.out.println(citiesNames.size());
        System.out.println(citiesNames.get(12345));
        List<Integer> numbers = new ArrayList<Integer>();
        List<Cities> numeroCiudades = new ArrayList<Cities>();
        for(int x=0; x<5;x++){
            numeroCiudades = citiesRepository.findByCity(citiesNames.get(x));
            numbers.add(numeroCiudades.size());
            System.out.println(x);
        }
        model.addAttribute("numbers",numbers);
        return "pieCharts";
    }


}
