export interface CatalogVehicle {
  id: number;
  brand: string;
  model: string;
  year: number;
  engineCode: string;
  aspirationType: string;
  factoryHorsepower: number;
  factoryTorque: number;
  factoryWeight: number;
  fipeBrandCode?: string;
  fipeModelCode?: string;
  fipeYearCode?: string;
}