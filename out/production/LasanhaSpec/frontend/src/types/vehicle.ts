export interface VehicleCard {

 id: number
 model: string
 engine: string
 year: number
 ownerName: string
 modificationsCount: number
 imageUrl: string

}




//tipo pra pagina completa
export interface VehicleDetail {

 id: number
 model: string
 engine: string
 year: number
 ownerName: string

 factoryHorsepower: number
 currentHorsepower: number

 factoryTorque: number
 currentTorque: number

 weight: number

 modifications: Modification[]

}



//tipo de modificação

export interface Modification {

 id: number
 name: string
 brand: string
 category: string

}






//define tipos de dados