package io.eyolas.example.server.domain;

import io.eyolas.example.server.domain.city.City;
import io.eyolas.example.server.domain.city.CityRepository;
import io.eyolas.example.server.domain.stadium.Stadium;
import io.eyolas.example.server.domain.stadium.StadiumRepository;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author dtouzet
 */
@Service
public class DatabaseServiceImpl implements DatabaseService{
    @Resource
    private CityRepository cityRepository;
    
    @Resource
    private StadiumRepository  stadiumRepository;
    
    @Transactional
    @Override
    public void initDatabase() {
        cityRepository.deleteAll();
        stadiumRepository.deleteAll();
        
        //Brisbane
        City city = new City("Australia", "Brisbane", "Queensland", "-27.470933, 153.023502");
        city = cityRepository.save(city);
        Stadium stadium = new Stadium(city, "Suncorp Stadium");
        stadiumRepository.save(stadium);
        
        //Melbourne
        city = new City("Australia", "Melbourne", "Victoria", "-37.813187, 144.96298");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Etihad Stadium");
        stadiumRepository.save(stadium);
        stadium = new Stadium(city, "Docklands Stadium");
        stadiumRepository.save(stadium);
        
        //Sydney
        city = new City("Australia", "Sydney", "New South Wales", "-33.868901, 151.207091");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Allianz Stadium");
        stadiumRepository.save(stadium);
        
        //CANADA
        //Montreal
        city = new City("Canada", "Montreal", "Quebec", "45.508889, -73.554167");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Olympic Stadium");
        stadiumRepository.save(stadium);
        
        //ISRAEL
         //Tel Aviv
        city = new City("Israel", "Tel Aviv", "", "32.066157, 34.777821");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Bloomfield Stadium");
        stadiumRepository.save(stadium);
        
        //JAPAN
        //Tokyo
        city = new City("Japan", "Tokyo", "", "35.689488, 139.691706");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "National Olympic Stadium");
        stadiumRepository.save(stadium);
        
        //SPAIN
        //Barcelona
        city = new City("Spain", "Barcelona", "Catalunya", "41.387917, 2.169919");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Camp Nou");
        stadiumRepository.save(stadium);
        
        //SWITZERLAND
        //Neuchatel
        city = new City("Switzerland", "Neuchatel", "", "46.992979, 6.931933");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "BSA Stadium Neuchâtel");
        stadiumRepository.save(stadium);
        
        //UNITED KINGDOM
        //Bath
        city = new City("UK", "Bath", "Somerset", "51.381428, -2.357454");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "The Recreation Ground");
        stadiumRepository.save(stadium);
        
        //London
        city = new City("UK", "London", "", "51.500152, -0.126236");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Wembley");
        stadiumRepository.save(stadium);
        stadium = new Stadium(city, "Twickenham");
        stadiumRepository.save(stadium);
        stadium = new Stadium(city, "Old Stratford");
        stadiumRepository.save(stadium);
        
        //Southampton
        city = new City("UK", "Southampton", "Hampshire", "50.902571, -1.397238");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "St Mary's Stadium");
        stadiumRepository.save(stadium);
        
        //USA
        //Atlanta
        city = new City("USA", "Atlanta", "GA", "33.748995, -84.387982");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Clark  Atlanta Stadium");
        stadiumRepository.save(stadium);
        
        //Chicago
        city = new City("USA", "Chicago", "IL", "41.878114, -87.629798");
        city = cityRepository.save(city);
        stadium = new Stadium(city, "Chicago Stadium");
        stadiumRepository.save(stadium);
        
    }
}
