export interface VehicleCard {
  id: number
  name: string
  engine: string
  imageUrl: string

  factoryHorsePower: number
  currentHorsePower: number
  horsepowerDiff: number
  horsepowerTrend: "POSITIVE" | "NEGATIVE" | "NEUTRAL"

  factoryTorque: number
  currentTorque: number
  torqueDiff: number
  torqueTrend: "POSITIVE" | "NEGATIVE" | "NEUTRAL"

  factoryWeight: number
  currentWeight: number
  weightDiff: number
  weightTrend: "POSITIVE" | "NEGATIVE" | "NEUTRAL"
}



//tipo pra pagina completa
export interface VehicleDetail {
  id: number;
  name: string;
  engine: string;

  factoryHorsePower: number;
  currentHorsePower: number;
  horsepowerDiff: number;
  horsepowerTrend: "POSITIVE" | "NEGATIVE" | "NEUTRAL";

  factoryTorque: number;
  currentTorque: number;
  torqueDiff: number;
  torqueTrend: "POSITIVE" | "NEGATIVE" | "NEUTRAL";

  factoryWeight: number;
  currentWeight: number;
  weightDiff: number;
  weightTrend: "POSITIVE" | "NEGATIVE" | "NEUTRAL";
}



//tipo de modificação

export interface Modification {

 id: number
 name: string
 brand: string
 category: string

}






//define tipos de dados