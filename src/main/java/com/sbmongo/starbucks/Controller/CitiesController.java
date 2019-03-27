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
        /*The names of the cities are taken by the method namesOfCities */
        List<String> citiesNames = namesOfCities();
        model.addAttribute("listCities", citiesNames);
        /* Create a list of with the name numbers that will contain the count of establishments per city. To insert
        the data in the list I use the method countByCity who's going to count the number of
        * establishments in each city. The count of cities is going to be stored in the list numbers*/
        List<Integer> numbers = new ArrayList<Integer>();
        for(int x=0; x<10;x++){
            numbers.add(citiesRepository.countByCity(citiesNames.get(x)));
        }
        model.addAttribute("numbers",numbers);
        return "pieCharts";
    }

    /*Send a cutted version of the cities names to the view for creating the checkbox for each city in the list*/
    @GetMapping("/selectCities")
    public ModelAndView selectCities(Model model,
                                     @RequestParam(name = "lista", required = false)String[] names){
        ModelAndView mav = new ModelAndView(ViewConstant.SELECT_CITIES);
        sendSelectedCities(model, names);
        return mav;
    }

    @GetMapping("/barCharts")
    public ModelAndView barCharts(Model model){
        ModelAndView mav = new ModelAndView(ViewConstant.BAR_CHART);
        sendFirstTen(model);
        return mav;
    }


    public void sendSelectedCities(Model model, String[] names){
        ModelAndView mav = new ModelAndView(ViewConstant.SELECT_CITIES);
        /*This list takes only the first 10 names of the DB and send it to the view for make a checkbox for each
         * element in the list*/
        List<String> citiesNames = recortarList();
        model.addAttribute("namesOfCities", citiesNames);
        /*I loops the variable 'names' that recieve the names selected by the checkboxes and count the number of
         * establishments for each name in the variable 'names'. After that it send it to the view for be used by the
         * highcharts*/
        List<Integer> numberOfStablishments = new ArrayList<Integer>();
        if(names != null){
            for(int x=0; x<names.length;x++){
                numberOfStablishments.add(citiesRepository.countByCity(names[x]));
            }

            model.addAttribute("numberOfStablishments", numberOfStablishments);
            model.addAttribute("selectedNames", names);
        }
    }

    /*This method uses the recortarList() method to take the first 10 names of the cities names list and send it to
    * the view for use them in the highchart names section. After that it counts the number of establishments per city
    * in each name of the citiesNames list*/
    public void sendFirstTen(Model model){
        List<String> citiesNames = recortarList();
        model.addAttribute("namesOfCities", citiesNames);
        List<Integer> numberOfStablishments = new ArrayList<Integer>();
        for(int x=0; x<citiesNames.size(); x++){
            numberOfStablishments.add(citiesRepository.countByCity(citiesNames.get(x)));
        }
        model.addAttribute("numberOfStablishments",numberOfStablishments);
    }

    public List<String> namesOfCities(){
        List<Cities> citiesList = citiesRepository.findAll();
        //Takes only the element City of the MongoDB and puts them in a list called citiesNames
        List<String> citiesNames = citiesList.stream().map(Cities::getCity).collect(Collectors.toList());
        //Cleans the repeated cities
        Set<String> uniqueCities = new HashSet<>(citiesNames);
        citiesNames.clear();
        citiesNames.addAll(uniqueCities);
        return citiesNames;
    }

    /*This method return a cutted list of the names of the databases for test purposes*/
    public List<String> recortarList(){
        List<String> citiesNames = namesOfCities();
        List<String> listaRecortada = new ArrayList<String>();
        for(int x=0; x<10; x++){
            listaRecortada.add(citiesNames.get(x));
        }
        return listaRecortada;
    }
}
