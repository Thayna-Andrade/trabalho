document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('form-problema');
  const msgSucesso = document.getElementById('msg-sucesso');
  const fotosInput = document.getElementById('fotos');
  const previewContainer = document.getElementById('preview-container');

  // Função auxiliar para pegar inputs pelo name
  const input = (name) => form.querySelector(`[name="${name}"]`);

  // Preview das fotos
  fotosInput.addEventListener('change', () => {
    previewContainer.innerHTML = '';
    const files = fotosInput.files;
    if (!files.length) return;

    for (const file of files) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const img = document.createElement('img');
        img.src = e.target.result;
        previewContainer.appendChild(img);
      };
      reader.readAsDataURL(file);
    }
  });

  // Envio do formulário
  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    // Validação básica
    if (!input('titulo').value.trim() || !input('descricao').value.trim()) {
      alert('Preencha todos os campos obrigatórios!');
      return;
    }

    try {
      // Preparar os dados para envio
      const formData = new FormData();
      formData.append('titulo', input('titulo').value);
      formData.append('descricao', input('descricao').value);
      formData.append('rua', input('Rua').value);
      formData.append('numero', input('Numero').value);
      formData.append('bairro', input('Bairro').value);
      formData.append('autor', input('autor').value);
      
      // TODO: Implementar upload real de fotos
      // Por enquanto, usando placeholder
      formData.append('fotoUrl', 'https://example.com/placeholder.jpg');

      // Enviar para a API
      const response = await fetch('/api/problemas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(formData)
      });

      const resultado = await response.json();

      if (resultado.success) {
        // Sucesso - limpar formulário e mostrar mensagem
        form.reset();
        previewContainer.innerHTML = '';
        msgSucesso.style.display = 'block';
        
        // Opcional: Redirecionar após sucesso
        setTimeout(() => {
          msgSucesso.style.display = 'none';
          // window.location.href = '/Codigo/src/main/resources/public/modulos/Cadastrar Problemas/Inicial.html';
        }, 3000);
      } else {
        alert('Erro: ' + resultado.message);
      }

    } catch (error) {
      console.error('Erro:', error);
      alert('Erro de conexão com o servidor');
    }
  });

  // Função para converter arquivos para Base64 (para uso futuro com upload de fotos)
  function fileToBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  // Função para fazer upload múltiplo de fotos (para implementação futura)
  async function uploadFotos(files) {
    const fotosUrls = [];
    
    for (const file of files) {
      try {
        // Converter para Base64
        const base64 = await fileToBase64(file);
        
        // TODO: Implementar lógica para enviar para servidor
        // e obter URL real da imagem
        // Por enquanto, usando placeholder
        fotosUrls.push('https://example.com/uploaded-' + Date.now() + '.jpg');
      } catch (error) {
        console.error('Erro ao processar foto:', error);
      }
    }
    
    return fotosUrls;
  }
});