package com.app.sakura.bootstrapper;

import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.TypeDetail;
import com.app.sakura.repository.FilterRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.TypeDetailRepository;
import com.app.sakura.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BootstrapperConfig {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private TypeDetailRepository typeDetailRepository;

    @PostConstruct
    public void fillData(){
        if(!FileUtil.doesDbExists()) {
            loadDefaultFilterType();
            loadDefaultManufacturerType();
            loadTypeDetails();
        }
    }

    private void loadTypeDetails() {
        String typeDetails1[] = {"Air Filter Metal (Primary)","Air Filter Metal (Secondary)","Air Filter Metal (Primary/Secondary)"
                ,"Air Filter Soft PU (Primary)","Air Filter Soft PU (Secondary)","Air Filter Soft PU (Primary/Secondary)","Cabin","Metal ","Soft PU"};
        String typeDetails2[] = {"Fuel Filter Separator","Fuel Filter Separator w/o Drain","Fuel Filter Primary","Fuel Filter Secondary","Fuel Filter Combination"
                ,"Hydraulic Filter","Fuel Water Separator"};
        String typeDetails3[] = {"Oil Filter","Oil Full Flow","Oil By Pass","Fuel Filter","Fuel Separator","Hydraulic","Fuel Filter Primary","Fuel Filter Secondary"};

        final Filter filter = filterRepository.findByName(getFilterName()[0]);
        List<TypeDetail> typeDetails = Arrays.asList(typeDetails1).stream()
                .map(x -> new TypeDetail(x.toUpperCase(),filter))
                .collect(Collectors.toList());

        typeDetailRepository.saveAll(typeDetails);

        final Filter filter2 = filterRepository.findByName(getFilterName()[1]);
        typeDetails = Arrays.asList(typeDetails2).stream()
                .map(x -> new TypeDetail(x.toUpperCase(),filter2))
                .collect(Collectors.toList());
        typeDetailRepository.saveAll(typeDetails);

        final Filter filter3 = filterRepository.findByName(getFilterName()[2]);
        typeDetails = Arrays.asList(typeDetails3).stream()
                .map(x -> new TypeDetail(x.toUpperCase(),filter3))
                .collect(Collectors.toList());

        typeDetailRepository.saveAll(typeDetails);
    }

    private String[] getFilterName(){
        return new String[]{"AIR FILTERS", "SPIN ON" , "ELEMENT PAPER TYPE"};
    }
    private void loadDefaultFilterType() {
        String filterNames[] = getFilterName();
        List<Filter> filters = Arrays.asList(filterNames).stream()
                .map(x -> new Filter(x))
                .collect(Collectors.toList());

        filterRepository.saveAll(filters);
    }


    private void loadDefaultManufacturerType() {
        String manufactureName[] = { "FUJI","QAIM","GUARD","LONG LIFE","PAKWIN","REX","ZIXS","SAKURA","FLEET GUARD","CLEAN","DONALDSON","MICRO","VIC","LINCLON"
                ,"HONDA","SUZUKI","TOYOTA","HINO","ISUZU","FAW","MASTER","SINO","HYNDAI","KIA","MAZDA","PERKINS","CASE","FIAT","FORD"
        };
        List<Manufacturer> manufacturers = Arrays.asList(manufactureName).stream()
                .map(x -> new Manufacturer(x))
                .collect(Collectors.toList());

        manufacturerRepository.saveAll(manufacturers);
    }
}
