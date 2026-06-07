export type AspirationType =
  | "NATURALLY_ASPIRATED"
  | "TURBOCHARGED"
  | "SUPERCHARGED";

export type FuelType =
  | "GASOLINE"
  | "ETHANOL"
  | "FLEX"
  | "DIESEL"
  | "HYBRID"
  | "ELECTRIC";

export type DriveType = "AWD" | "FWD" | "RWD";

export type TransmissionType =
  | "MANUAL"
  | "AUTOMATIC"
  | "AUTOMATED_MANUAL"
  | "DUAL_CLUTCH"
  | "CVT";

export type TransmissionModel =
  | "AL4" | "AISIN_AT6" | "ZF_8HP"
  | "DUALOGIC" | "I_MOTION" | "EASYTRONIC"
  | "DSG" | "POWERSHIFT"
  | "CVT_JATCO" | "CVT_HONDA"
  | "MANUAL_4" | "MANUAL_5" | "MANUAL_6";

export interface CatalogVehicle {
  id: number;
  brand: string;
  model: string;
  year: number;
  engineCode: string;
  aspirationType: AspirationType;
  factoryHorsepower: number;
  factoryTorque: number;
  factoryWeight: number;
  displacement?: number;
  cylinderCount?: number;
  topSpeed?: number;
  acceleration0to100?: number;
  fuelType?: FuelType;
  driveType?: DriveType;
  transmissionType?: TransmissionType;
  transmissionModel?: TransmissionModel;
  gearCount?: number;
  fipeBrandCode?: string;
  fipeModelCode?: string;
  fipeYearCode?: string;
}

export interface CatalogVehicleForm {
  brand: string;
  model: string;
  year: number;
  engineCode: string;
  aspirationType: AspirationType;
  factoryHorsePower: number;
  factoryTorque: number;
  factoryWeight: number;
  displacement: number;
  cylinderCount: number;
  topSpeed: number;
  acceleration0to100: number;
  fuelType: FuelType;
  driveType: DriveType;
  transmissionType: TransmissionType;
  transmissionModel: TransmissionModel;
  gearCount: number;
  fipeBrandCode: string;
  fipeModelCode: string;
  fipeYearCode: string;
}