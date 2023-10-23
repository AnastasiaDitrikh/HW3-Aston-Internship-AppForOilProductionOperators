import model.Location;
import model.Operator;
import model.Well;
import model.WellCluster;
import service.OperatorService;
import service.WellService;

public class DaoRunnerForTest {

    public static void main(String[] args) {

        var operatorService = OperatorService.getOperatorService();
        var operators = operatorService.findAll();
        System.out.println(operators);
//        var operator=operatorService.getById(1L);
//        operator.ifPresent(operatorUpdated -> {
//            operatorUpdated.setSurname("Minin");
//            operatorUpdated.setName("Petr");
//            operatorService.update(operatorUpdated);
//        });
//        System.out.println(operator);
        //Operator operator = new Operator(3L,"Maksim","Sidorov");
        //Operator savedOperator = operatorService.save(operator);

//        var locationService = LocationService.getLocationService();
       // Location location= new Location(1L,47.5, 48.96);
//        Location savedLocation = locationService.save(location);

//        var wellClusterService = WellClusterService.getWellClusterService();
       // WellCluster wellCluster = new WellCluster(1L, 23,location,operator);
//        WellCluster savedWellCluster = wellClusterService.save(wellCluster);
 //       System.out.println(savedWellCluster);


//        var wellService = WellService.getWellService();
//        Well well = new Well("ESP",145.3, wellCluster);
//        Well savedWell = wellService.save(well);
//        System.out.println(savedWell);
    }
}