import { useState, useRef, useEffect } from "react";
import "./AccountSettingsPage.css";
import { updateMe, getMe, uploadProfileImage } from "../api/updateUserApi";

interface UserForm {
  username: string;
  email: string;
  avatarUrl: string | null;
  fullName: string;
}

export default function AccountSettingsPage() {

  const [uploadingImage, setUploadingImage] = useState(false);
  const [imageSaved, setImageSaved] = useState(false);

  const [form, setForm] = useState<UserForm>({
    username: "",
    email: "",
    avatarUrl: null,
    fullName: "",
  });

  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  useEffect(() => {
    loadUser();
  }, []);

  async function loadUser() {
    try {
      const user = await getMe();

      setForm({
        username: user.username,
        email: user.email,
        avatarUrl: user.profileImageUrl ?? null,
        fullName: user.fullName ?? "",
      });

      if (user.profileImageUrl) {
        setPreview(user.profileImageUrl);
      }
    } catch (err) {
      console.error("Erro ao carregar usuário", err);
    }
  }

  const [preview, setPreview] = useState<string | null>(null);
  const [saving, setSaving] = useState(false);
  const [saved, setSaved] = useState(false);
  const [passwordSent, setPasswordSent] = useState(false);
  const fileRef = useRef<HTMLInputElement>(null);

  async function handleImageUpload() {
    if (!selectedFile) return;

    try {
      setUploadingImage(true);

      const imageUrl =
        await uploadProfileImage(selectedFile);

      setPreview(imageUrl);

      setImageSaved(true);

      setTimeout(() => {
        setImageSaved(false);
      }, 3000);

      await loadUser();

    } catch (err) {
      console.error(err);
      alert("Erro ao enviar imagem");
    } finally {
      setUploadingImage(false);
    }
  }

  function handleFileChange(e: React.ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0];

    if (!file) return;

    setSelectedFile(file);

    const url = URL.createObjectURL(file);
    setPreview(url);
  }

  async function handleSave() {
    try {
      setSaving(true);
      setSaved(false);

      await updateMe({
        username: form.username,
        fullName: form.fullName
      });

      await loadUser();

      setSaved(true);

      setTimeout(() => {
        setSaved(false);
      }, 3000);

    } catch (error) {
      console.error(error);
      alert("Erro ao atualizar usuário");
    } finally {
      setSaving(false);
    }
  }

  function handlePasswordReset() {
    setPasswordSent(true);
    setTimeout(() => setPasswordSent(false), 4000);
  }

  const initials = form.username.slice(0, 2).toUpperCase();

  return (
    <div className="settings-page">
      <div className="settings-header">
        <h1>Account Settings</h1>
        <p>Gerencie seu perfil e credenciais de acesso.</p>
      </div>

      <div className="settings-grid">
        {/* ── Coluna esquerda: avatar ── */}
        <div className="settings-avatar-card">
          <p className="settings-section-label">Foto de perfil</p>

          <div className="settings-avatar-wrap">
            {preview ? (
              <img src={preview} alt="avatar" className="settings-avatar-img" />
            ) : (
              <div className="settings-avatar-placeholder">{initials}</div>
            )}
          </div>

          <button
  className="settings-upload-btn"
  onClick={() => fileRef.current?.click()}
>
  <svg
    width="13"
    height="13"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2.2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
    <polyline points="17 8 12 3 7 8" />
    <line x1="12" y1="3" x2="12" y2="15" />
  </svg>
  Alterar foto
</button>

{selectedFile && (
  <button
    className="settings-save-image-btn"
    onClick={handleImageUpload}
    disabled={uploadingImage}
  >
    {uploadingImage
      ? "Enviando..."
      : imageSaved
      ? "Imagem salva!"
      : "Salvar foto"}
  </button>
)}
          <input
            ref={fileRef}
            type="file"
            accept="image/*"
            style={{ display: "none" }}
            onChange={handleFileChange}
          />

          {preview && (
            <button
              className="settings-remove-btn"
              onClick={() => { setPreview(null); if (fileRef.current) fileRef.current.value = ""; }}
            >
              Remover foto
            </button>
          )}

          <p className="settings-avatar-hint">JPG, PNG ou WebP · máx. 2 MB</p>
        </div>

        {/* ── Coluna direita: campos ── */}
        <div className="settings-fields-card">
          <p className="settings-section-label">Informações da conta</p>

          <div className="settings-field">
            <label htmlFor="fullName">Nome completo</label>
            <input
              id="fullName"
              type="text"
              value={form.fullName}
              onChange={(e) =>
                setForm({
                  ...form,
                  fullName: e.target.value,
                })
              }
              placeholder="João Pedro Silva"
            />
          </div>

          <div className="settings-field">
            <label htmlFor="username">Nome de usuário</label>
            <input
              id="username"
              type="text"
              value={form.username}
              onChange={(e) => setForm({ ...form, username: e.target.value })}
              placeholder="seu_usuario"
            />
          </div>

          <div className="settings-field">
          <label htmlFor="email">E-mail</label>

          <input
            id="email"
            type="email"
            value={form.email}
            readOnly
            className="settings-disabled-input"
          />

          <span className="settings-disabled-hint">
            O e-mail não pode ser alterado.
          </span>
        </div>

          <div className="settings-actions">
            <button
              className="settings-save-btn"
              onClick={handleSave}
              disabled={saving}
            >
              {saving ? (
                <>
                  <span className="settings-btn-spinner" />
                  Salvando...
                </>
              ) : saved ? (
                <>
                  <svg width="14" height="14" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                  </svg>
                  Salvo!
                </>
              ) : (
                "Salvar alterações"
              )}
            </button>
          </div>
        </div>
      </div>

      {/* ── Segurança ── */}
      <div className="settings-security-card">
        <div className="settings-security-info">
          <p className="settings-section-label">Segurança</p>
          <p className="settings-security-desc">
            Enviaremos um link de redefinição para o e-mail <strong>{form.email}</strong>.
          </p>
        </div>

        <button
          className={`settings-password-btn ${passwordSent ? "sent" : ""}`}
          onClick={handlePasswordReset}
          disabled={passwordSent}
        >
          {passwordSent ? (
            <>
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none"
                stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                <polyline points="20 6 9 17 4 12"/>
              </svg>
              E-mail enviado
            </>
          ) : (
            <>
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none"
                stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
              Recuperar senha
            </>
          )}
        </button>
      </div>
    </div>
  );
}
