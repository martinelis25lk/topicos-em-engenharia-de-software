import api from "./api";

export interface VehicleChronicSummary {
  id: number;
  name: string;
  imageUrl: string | null;
  reliabilityScore: number;
  avgAnnualCost: number;
  criticalFailureRate: number;
  topIssueNames: string[];
}

export interface ChronicIssueCard {
  id: number;
  title: string;
  description: string;
  severity: "LOW" | "MEDIUM" | "HIGH" | "CRITICAL";
  millageMin: number;
  millageMax: number;
  costMin: number;
  costMax: number;
  occurrences: number;
  usefulVotes: number;
  notUsefulVotes: number;
}

export interface VehicleChronicPage {
  name: string;
  year: number;
  imgUrl: string | null;
  documentedIssues: number;
  reliabilityScore: number;
  avgAnnualCost: number;
  criticalFailureRate: number;
  issues: ChronicIssueCard[];
}

export interface OccurrenceReport {
  username: string | null;
  repairCost: number;
  millageAtOccurrence: number;
  description: string;
}

export interface ChronicIssueDetail {
  chronicIssueCardDTO: ChronicIssueCard;
  symptoms: string[];
  preventiveMaintenance: string[];
  affectedEngines: string;
  affectedYears: string;
  repairComplexity: "DIY" | "INTERMEDIATE" | "PROFESSIONAL";
  occurrenceReports: OccurrenceReport[];
}

export interface CreateChronicIssueDTO {
  vehicleCatalogModelId: number;
  title: string;
  description: string;
  severity: string;
  category: string;
  millageMin: number;
  millageMax: number;
  costMin: number;
  costMax: number;
  affectedEngines: string;
  affectedYears: string;
  repairComplexity: string;
  createdByUserId: number;
  symptoms: string[];
  preventiveMaintenance: string[];
}

export const getAllModelsWithIssues = async (): Promise<VehicleChronicSummary[]> => {
  const res = await api.get("/chronic-issues/models");
  return res.data;
};

export const getModelChronicPage = async (modelId: number): Promise<VehicleChronicPage> => {
  const res = await api.get(`/chronic-issues/models/${modelId}`);
  return res.data;
};

export const getIssueDetail = async (issueId: number): Promise<ChronicIssueDetail> => {
  const res = await api.get(`/chronic-issues/${issueId}`);
  return res.data;
};

export const createChronicIssue = async (dto: CreateChronicIssueDTO): Promise<number> => {
  const res = await api.post("/chronic-issues", dto);
  return res.data;
};

export const voteOnIssue = async (issueId: number, voteType: "USEFUL" | "NOT_USEFUL"): Promise<void> => {
  await api.post(`/chronic-issues/${issueId}/vote`, { voteType });
};

export const reportOccurrence = async (
  issueId: number,
  dto: { vehicleId: number; millageAtOccurrence: number; repairCost: number; description: string }
): Promise<void> => {
  await api.post(`/chronic-issues/${issueId}/occurrence`, dto);
};

export const approveIssue = async (issueId: number): Promise<void> => {
  await api.patch(`/chronic-issues/${issueId}/approve`);
};

export const rejectIssue = async (issueId: number): Promise<void> => {
  await api.patch(`/chronic-issues/${issueId}/reject`);
};

export const uploadProfileImage = async (file: File): Promise<string> => {
  const formData = new FormData();
  formData.append("file", file);
  const res = await api.post("/users/me/profile-image", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
  return res.data;
};