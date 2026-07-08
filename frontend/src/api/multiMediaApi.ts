import axios from "axios";

// URL base apontando para o Controller do seu Spring Boot
const API_URL = "http://localhost:8080/api/multimedia";

// ── Tipagens dos Modelos (Refletindo o seu Backend) ────────────────
export interface Midia {
  id: number;
  nome: string;
  caminhoArquivo: string;
  dataCadastro: string;
  // Adicione outras propriedades que o seu modelo 'Midia' do Java possua
}

export type TipoMidia = "imagem" | "audio" | "video";

// ── Objeto de Integração com a API ─────────────────────────────────
export const multiMediaApi = {
  
  /**
   * Opção 4 - Listar todas as mídias salvas no banco
   * 💡 Ajustado para incluir o Token JWT igual ao método cadastrar
   */
  listar: async (): Promise<Midia[]> => {
    const response = await axios.get<Midia[]>(API_URL, {
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response.data;
  },

  /**
   * Opção 5 - Buscar os metadados de uma mídia específica por ID
   */
  buscarPorId: async (id: number): Promise<Midia> => {
    const response = await axios.get<Midia>(`${API_URL}/${id}`, {
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response.data;
  },

  /**
   * Opções 1, 2 e 3 - Cadastrar/Importar uma mídia enviando o arquivo físico para o S3
   * @param tipo Define qual rota chamar: 'imagem', 'audio' ou 'video'
   * @param nome Nome amigável digitado pelo usuário
   * @param file Arquivo binário capturado pelo <input type="file" />
   */
  cadastrar: async (tipo: TipoMidia, nome: string, file: File): Promise<Midia> => {
    console.log("Enviando arquivo para o backend:", nome, file.name);
    const formData = new FormData();
    formData.append("nome", nome);
    formData.append("file", file);

    const response = await axios.post<Midia>(`${API_URL}/${tipo}`, formData, {
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`, // Adiciona o token JWT no cabeçalho
      },
    });
    return response.data;
  },

  /**
   * Opção 6 - Remover arquivo físico hospedado no S3 utilizando a chave gerada pela AWS
   * @param chaveS3 O nome grande/hash do arquivo gravado no banco (ex: "706a0aea..._foto.jpg")
   */
  deletar: async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`, {
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      },
    });
  }
};