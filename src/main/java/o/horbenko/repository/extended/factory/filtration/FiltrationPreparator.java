package o.horbenko.repository.extended.factory.filtration;


import java.util.List;

public interface FiltrationPreparator {

    List<String> prepareRawInputString(String rawInputJsonAsString);

}
