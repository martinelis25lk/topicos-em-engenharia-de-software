package br.com.lasanhaspec.carservice.domain.enums;



public enum IssueSeverity {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(6);


    private final int weight;

    IssueSeverity(int weight){
        this.weight = weight;
    }


    public int getWeight(){
        return weight;
    }
}
