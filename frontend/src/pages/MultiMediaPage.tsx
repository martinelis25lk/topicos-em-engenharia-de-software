import { useState, useEffect, type ChangeEvent, type FormEvent } from "react"; 
import type { TipoMidia } from "../api/multiMediaApi"; 
import { multiMediaApi } from "../api/multiMediaApi"; 

const MultimediaPage = () => {
  // Controle de exibição do Modal de cadastro
  const [showModal, setShowModal] = useState<boolean>(false);
  
  // Lista de mídias cadastradas do usuário
  const [midias, setMidias] = useState<any[]>([]);
  const [midiaExpandida, setMidiaExpandida] = useState<any | null>(null);
  const [carregandoMidias, setCarregandoMidias] = useState<boolean>(false);
  
  // 🔍 NOVO: Estado para armazenar o termo da pesquisa
  const [termoPesquisa, setTermoPesquisa] = useState<string>("");

  // Estados do formulário de cadastro
  const [nome, setNome] = useState<string>("");
  const [tipo, setTipo] = useState<TipoMidia>("imagem");
  const [arquivo, setArquivo] = useState<File | null>(null);
  
  // Estados de feedback visual
  const [enviando, setEnviando] = useState<boolean>(false);
  const [erro, setErro] = useState<string | null>(null);

  // Efeito para carregar as mídias ao abrir a página
  useEffect(() => {
    carregarMinhasMidias();
  }, []);

  // Busca as mídias vinculadas ao username atual
  const carregarMinhasMidias = async () => {
    setCarregandoMidias(true);
    try {
      const dados = await multiMediaApi.listar();
      setMidias(dados);
    } catch (err) {
      console.error("Erro ao buscar mídias:", err);
    } finally {
      setCarregandoMidias(false);
    }
  };

  // Captura o arquivo do input do navegador
  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setArquivo(e.target.files[0]);
    }
  };

  // Remove uma mídia da lista e do servidor
  const handleDeletar = async (id: number) => {
    if (window.confirm("Tem certeza que deseja deletar esta mídia permanentemente?")) {
      try {
        await multiMediaApi.deletar(id);
        setMidias((midiasAtuais) => midiasAtuais.filter((midia) => midia.id !== id));
        alert("Mídia removida com sucesso!");
      } catch (err) {
        console.error("Erro ao deletar mídia:", err);
        alert("Não foi possível deletar a mídia. Verifique suas permissões.");
      }
    }
  };

  // Limpa o formulário e fecha o modal
  const fecharModal = () => {
    setShowModal(false);
    setNome("");
    setTipo("imagem");
    setArquivo(null);
    setErro(null);
  };

  // Envia os dados para o Spring Boot através da API
  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    
    if (!nome.trim() || !arquivo) {
      setErro("Por favor, preencha o nome e selecione um arquivo.");
      return;
    }

    setEnviando(true);
    setErro(null);

    try {
      const midiaSalva = await multiMediaApi.cadastrar(tipo, nome, arquivo);
      console.log("Mídia salva com sucesso! ID:", midiaSalva.id);
      
      alert("Mídia importada com sucesso!");
      fecharModal();
      
      carregarMinhasMidias();
    } catch (err: any) {
      console.error(err);
      setErro(err.response?.data || "Erro ao conectar com o servidor.");
    } finally {
      setEnviando(false);
    }
  };

  // 🔍 NOVO: Filtra as mídias na memória antes de renderizar na tela
  // Procura tanto no título/nome quanto no tipo da mídia (caso digitem 'imagem', 'video', etc)
  const midiasFiltradas = midias.filter((midia) => {
    const termo = termoPesquisa.toLowerCase();
    return (
      midia.nome.toLowerCase().includes(termo) ||
      midia.tipo.toLowerCase().includes(termo) ||
      midia.username?.toLowerCase().includes(termo)
    );
  });

  return (
    <div className="garage-page multimedia-page">
      
      {/* ── Header ──────────────────────────────────────── */}
      <div className="garage-header">
        <div className="garage-header-text">
          <h1>Multimídias</h1>
          <p>Gerencie as imagens, vídeos e documentos do seu projeto</p>
        </div>
        <div className="garage-header-right">
          <button 
            className="add-vehicle-btn"
            onClick={() => setShowModal(true)}
            title="Importar nova mídia"
          >
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" style={{ marginRight: '8px' }}>
              <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
              <polyline points="17 8 12 3 7 8" />
              <line x1="12" y1="3" x2="12" y2="15" />
            </svg>
            Importar Mídia
          </button>
        </div>
      </div>

      {/* ── 🔍 NOVO: Barra de Busca (Só aparece se o banco tiver alguma mídia) ── */}
      {midias.length > 0 && (
        <div style={styles.searchContainer}>
          <div style={styles.searchIconWrapper}>
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#888" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
          </div>
          <input
            type="text"
            placeholder="Buscar mídias por nome, tipo ou quem enviou..."
            value={termoPesquisa}
            onChange={(e) => setTermoPesquisa(e.target.value)}
            style={styles.searchInput}
          />
          {termoPesquisa && (
            <button onClick={() => setTermoPesquisa("")} style={styles.clearSearchBtn}>
              &times;
            </button>
          )}
        </div>
      )}

      {/* ── Conteúdo / Grade de Mídias ──────────────────── */}
      {carregandoMidias ? (
        <div style={{ textAlign: "center", padding: "40px", color: "#666" }}>Carregando mídias...</div>
      ) : midias.length > 0 ? (
        // Se o usuário digitou algo e o filtro limpou a lista inteira
        midiasFiltradas.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '40px', color: '#666' }}>
            Nenhum arquivo encontrado para "<strong>{termoPesquisa}</strong>".
          </div>
        ) : (
          // 💡 Trocamos 'midias.map' por 'midiasFiltradas.map'
          <div className="media-grid" style={styles.grid}>
            {midiasFiltradas.map((midia) => {
              const podeDeletar = true;

              return (
                <div 
                  key={midia.id} 
                  onClick={() => setMidiaExpandida(midia)}
                  style={{ 
                    position: 'relative', 
                    border: '1px solid #e0e0e0', 
                    padding: '20px', 
                    borderRadius: '12px',
                    boxShadow: '0 4px 6px rgba(0,0,0,0.05)',
                    backgroundColor: '#fff',
                    transition: 'transform 0.2s',
                    cursor: 'pointer'
                  }}
                >
                  
                  {/* ❌ Botão de deletar condicional */}
                  {podeDeletar && (
                    <button 
                      onClick={(e) => {
                        e.stopPropagation();
                        handleDeletar(midia.id);
                      }}
                      style={{
                        position: 'absolute',
                        top: '12px',
                        right: '12px',
                        backgroundColor: '#ff4d4d',
                        color: 'white',
                        border: 'none',
                        borderRadius: '50%',
                        width: '26px',
                        height: '26px',
                        cursor: 'pointer',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        fontWeight: 'bold',
                        fontSize: '14px',
                        boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
                        zIndex: 10
                      }}
                      onMouseEnter={(e) => e.currentTarget.style.backgroundColor = '#cc0000'}
                      onMouseLeave={(e) => e.currentTarget.style.backgroundColor = '#ff4d4d'}
                      title="Excluir mídia permanentemente"
                    >
                      &times;
                    </button>
                  )}

                  {/* 📁 Cabeçalho e Identificação do Card */}
                  <div style={{ marginBottom: '12px', paddingRight: '20px' }}>
                    <h3 style={{ margin: '0 0 4px 0', fontSize: '18px', color: '#333' }}>
                      {midia.nome}
                    </h3>
                    <span style={{ 
                      fontSize: '11px', 
                      backgroundColor: '#f0f0f0', 
                      padding: '3px 8px', 
                      borderRadius: '4px',
                      color: '#666',
                      textTransform: 'uppercase',
                      fontWeight: 'bold'
                    }}>
                      {midia.tipo}
                    </span>
                  </div>

                  {/* 🎮 Renderização de acordo com o tipo da mídia */}
                  <div style={{ marginTop: '15px' }}>
                    {midia.tipo === 'IMAGEM' && (
                      <img 
                        src={midia.caminhoArquivo} 
                        alt={midia.nome} 
                        style={{ width: '100%', maxHeight: '200px', objectFit: 'cover', borderRadius: '6px' }} 
                      />
                    )}

                    {midia.tipo === 'AUDIO' && (
                      <div onClick={(e) => e.stopPropagation()}>
                        <audio controls src={midia.caminhoArquivo} style={{ width: '100%' }}>
                          Seu navegador não suporta o elemento de áudio.
                        </audio>
                      </div>
                    )}

                    {midia.tipo === 'VIDEO' && (
                      <div onClick={(e) => e.stopPropagation()}>
                        <video controls src={midia.caminhoArquivo} style={{ width: '100%', borderRadius: '6px' }}>
                          Seu navegador não suporta o elemento de vídeo.
                        </video>
                      </div>
                    )}
                  </div>

                  {/* 👤 Detalhes adicionais no rodapé do card */}
                  <div style={{ marginTop: '15px', borderTop: '1px solid #f0f0f0', paddingTop: '10px', fontSize: '12px', color: '#999' }}>
                    Enviado por: <strong style={{ color: '#555' }}>{midia.username}</strong>
                  </div>

                </div>
              );
            })}
          </div>
        )
      ) : (
        <div className="media-empty-state" style={styles.emptyState}>
          <p style={{ margin: 0, fontWeight: "500" }}>Nenhuma mídia encontrada.</p>
          <button 
            onClick={() => setShowModal(true)} 
            style={{ background: "none", border: "none", color: "#007bff", cursor: "pointer", padding: "4px", textDecoration: "underline" }}
          >
            Clique aqui para importar seu primeiro arquivo
          </button>
        </div>
      )}

      {/* ── Modal de Cadastro ─────────────────────────── */}
      {showModal && (
        <div className="modal-overlay" style={styles.overlay}>
          <div className="modal-content" style={styles.modal}>
            <div className="modal-header" style={styles.header}>
              <h2>Importar Nova Mídia</h2>
              <button onClick={fecharModal} style={styles.closeBtn}>&times;</button>
            </div>

            <form onSubmit={handleSubmit} style={styles.form}>
              {erro && <div style={styles.errorAlert}>{erro}</div>}

              <div className="form-group" style={styles.formGroup}>
                <label style={styles.label}>Nome:</label>
                <input 
                  type="text" 
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  placeholder="Ex: Foto do motor, Ronco do escape..."
                  style={styles.input}
                  required
                />
              </div>

              <div className="form-group" style={styles.formGroup}>
                <label style={styles.label}>Tipo de Mídia:</label>
                <select 
                  value={tipo}
                  onChange={(e) => setTipo(e.target.value as TipoMidia)}
                  style={styles.select}
                >
                  <option value="imagem">Imagem (PNG, JPG, JPEG)</option>
                  <option value="audio">Áudio (MP3, WAV)</option>
                  <option value="video">Vídeo (MP4)</option>
                </select>
              </div>

              <div className="form-group" style={styles.formGroup}>
                <label style={styles.label}>Selecionar Arquivo:</label>
                <input 
                  type="file" 
                  onChange={handleFileChange}
                  accept={tipo === "imagem" ? "image/*" : tipo === "audio" ? "audio/*" : "video/*"}
                  style={styles.fileInput}
                  required
                />
              </div>

              <div className="form-actions" style={styles.actions}>
                <button 
                  type="button" 
                  onClick={fecharModal} 
                  style={styles.cancelBtn}
                  disabled={enviando}
                >
                  Cancelar
                </button>
                <button 
                  type="submit" 
                  style={styles.submitBtn}
                  disabled={enviando}
                >
                  {enviando ? "Enviando..." : "Enviar para o S3"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* ── Modal de Visão Expandida (Zoom) ─────────────────────────── */}
      {midiaExpandida && (
        <div onClick={() => setMidiaExpandida(null)} style={styles.zoomOverlay}>
          <div onClick={(e) => e.stopPropagation()} style={styles.zoomModal}>
            <button onClick={() => setMidiaExpandida(null)} style={styles.zoomCloseBtn}>&times;</button>

            <h2 style={{ marginTop: 0, marginBottom: '5px' }}>{midiaExpandida.nome}</h2>
            <p style={{ color: '#666', fontSize: '14px', marginBottom: '20px' }}>Enviado por: {midiaExpandida.username}</p>

            <div style={styles.zoomContentContainer}>
              {midiaExpandida.tipo === 'IMAGEM' && (
                <img src={midiaExpandida.caminhoArquivo} alt={midiaExpandida.nome} style={{ maxWidth: '100%', maxHeight: '65vh', borderRadius: '8px' }} />
              )}

              {midiaExpandida.tipo === 'AUDIO' && (
                <div style={{ width: '100%', padding: '20px 0' }}>
                  <audio controls src={midiaExpandida.caminhoArquivo} style={{ width: '100%' }} autoPlay />
                </div>
              )}

              {midiaExpandida.tipo === 'VIDEO' && (
                <video controls src={midiaExpandida.caminhoArquivo} style={{ maxWidth: '100%', maxHeight: '65vh', borderRadius: '8px' }} autoPlay />
              )}
            </div>
          </div>
        </div>
      )}

    </div>
  );
};

const styles = {
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))",
    gap: "20px",
    padding: "20px 0"
  },
  // 🔍 Novos Estilos para o Input de Busca:
  searchContainer: {
    position: "relative" as const,
    width: "100%",
    maxWidth: "500px",
    margin: "0 0 20px 0",
    display: "flex",
    alignItems: "center"
  },
  searchIconWrapper: {
    position: "absolute" as const,
    left: "14px",
    display: "flex",
    alignItems: "center",
    pointerEvents: "none" as const
  },
  searchInput: {
    width: "100%",
    padding: "12px 40px 12px 42px",
    borderRadius: "8px",
    border: "1px solid #ccc",
    fontSize: "14px",
    outline: "none",
    boxShadow: "0 2px 4px rgba(0,0,0,0.02)",
    transition: "border-color 0.2s"
  },
  clearSearchBtn: {
    position: "absolute" as const,
    right: "12px",
    background: "none",
    border: "none",
    fontSize: "20px",
    color: "#999",
    cursor: "pointer",
    padding: "4px"
  },
  emptyState: {
    textAlign: "center" as const,
    padding: "60px 20px",
    color: "#666",
    border: "2px dashed #ccc",
    borderRadius: "8px",
    marginTop: "20px",
    display: "flex",
    flexDirection: "column" as const,
    gap: "8px"
  },
  overlay: {
    position: "fixed" as const,
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: "rgba(0, 0, 0, 0.6)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 1000,
  },
  modal: {
    backgroundColor: "#fff",
    padding: "24px",
    borderRadius: "8px",
    width: "100%",
    maxWidth: "500px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
    color: "#333"
  },
  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: "20px",
    borderBottom: "1px solid #eee",
    paddingBottom: "10px"
  },
  closeBtn: {
    background: "none",
    border: "none",
    fontSize: "24px",
    cursor: "pointer",
    color: "#666"
  },
  form: {
    display: "flex",
    flexDirection: "column" as const,
    gap: "16px"
  },
  formGroup: {
    display: "flex",
    flexDirection: "column" as const,
    gap: "6px"
  },
  label: {
    fontWeight: "bold" as const,
    fontSize: "14px"
  },
  input: {
    padding: "10px",
    borderRadius: "4px",
    border: "1px solid #ccc",
    fontSize: "14px"
  },
  select: {
    padding: "10px",
    borderRadius: "4px",
    border: "1px solid #ccc",
    fontSize: "14px",
    backgroundColor: "#0e8aac"
  },
  fileInput: {
    padding: "6px 0"
  },
  actions: {
    display: "flex",
    justifyContent: "flex-end",
    gap: "12px",
    marginTop: "10px"
  },
  cancelBtn: {
    padding: "10px 16px",
    borderRadius: "4px",
    border: "1px solid #ccc",
    backgroundColor: "#c90505",
    cursor: "pointer"
  },
  submitBtn: {
    padding: "10px 16px",
    borderRadius: "4px",
    border: "none",
    backgroundColor: "#007bff",
    color: "#fff",
    cursor: "pointer",
    fontWeight: "bold" as const
  },
  errorAlert: {
    backgroundColor: "#f8d7da",
    color: "#721c24",
    padding: "10px",
    borderRadius: "4px",
    fontSize: "14px"
  },
  zoomOverlay: {
    position: "fixed" as const,
    top: 0,
    left: 0,
    width: "100vw",
    height: "100vh",
    backgroundColor: "rgba(0, 0, 0, 0.85)",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    zIndex: 9999,
    padding: "20px"
  },
  zoomModal: {
    backgroundColor: "#fff",
    padding: "25px",
    borderRadius: "16px",
    maxWidth: "800px",
    width: "100%",
    boxShadow: "0px 10px 30px rgba(0,0,0,0.3)",
    position: "relative" as const
  },
  zoomCloseBtn: {
    position: "absolute" as const,
    top: "-15px",
    right: "-15px",
    backgroundColor: "#333",
    color: "#fff",
    border: "none",
    borderRadius: "50%",
    width: "32px",
    height: "32px",
    fontSize: "20px",
    cursor: "pointer",
    boxShadow: "0 2px 8px rgba(0,0,0,0.3)"
  },
  zoomContentContainer: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f9f9f9",
    borderRadius: "8px",
    padding: "10px"
  }
};

export default MultimediaPage;