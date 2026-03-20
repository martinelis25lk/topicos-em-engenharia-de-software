export interface VehicleCard {
  id: number
  name: string
  engine: string
  imageUrl: string

  factoryHorsePower: number
  currentHorsePower: number

  factoryTorque: number
  currentTorque: number

  factoryWeight: number
  currentWeight: number

  modificationsCount: number
  powerGainPercentage: number
}




//tipo pra pagina completa
export interface VehicleDetail {
  model: ReactNode
  id: number;
  name: string;
  engine: string;
  factoryHorsePower: number;
  currentHorsePower: number;
  factoryTorque: number;
  currentTorque: number;
  factoryWeight: number;
  currentWeight: number;
  ownerName: string;
  modifications?: Modification[]; // se quiser trazer detalhes das mods
}



//tipo de modificação

export interface Modification {

 id: number
 name: string
 brand: string
 category: string

}






//define tipos de dados