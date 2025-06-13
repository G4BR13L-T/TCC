package com.tcc.TCC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControleDeDistribuicaoDeNotebookApplication {
	/**
	 * 4 usuarios: super, gestor, biblioteca, professor;
	 * Requisitos
	 *
	 * *Professores/bibliotecários sempre recebem contas feitas por gerentes.
	 * *Professores/bibliotecários não podem criar suas próprias contas.
	 * *Admin possui liberdade total no sistema e nas contas gerente, professores e bibliotecários.
	 * *A biblioteca confirma todas as devoluções de notebooks.
	 * *O sistema deve verificar a quantidade de notebooks disponíveis antes de permitir a reserva concreta.
	 * *Professores apenas reservam notebooks.
	 * *Bibliotecários adicionam novos notebooks no limite.
	 * *Todos podem reservar notebooks.
	 * *Gerentes têm capacidades CRUD para contas de professores e bibliotecários e registros de notebooks.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ControleDeDistribuicaoDeNotebookApplication.class, args);
	}

}

/*
“Você foi rejeitado pelo sistema. Eu sou apenas a resposta que ele não conseguiu processar.”

pip install reportlab pillow


from reportlab.lib.pagesizes import A4
from reportlab.pdfgen import canvas
from reportlab.lib.units import cm
from reportlab.lib.utils import ImageReader
from reportlab.lib import colors
from PIL import Image, ImageDraw, ImageFont
import io

def create_uniform_part_image(name, color, size=(200, 200)):
    """Cria uma imagem simples representando uma parte do uniforme."""
    img = Image.new('RGB', size, (20, 20, 20))
    draw = ImageDraw.Draw(img)
    # Retângulo central
    draw.rectangle([20, 20, size[0]-20, size[1]-20], fill=color)
    # Texto da parte
    try:
        font = ImageFont.truetype("arial.ttf", 24)
    except:
        font = ImageFont.load_default()
    w, h = draw.textsize(name, font=font)
    draw.text(((size[0]-w)/2, (size[1]-h)/2), name, fill="white", font=font)
    # Retorna imagem como objeto que pode ser lido no reportlab
    bio = io.BytesIO()
    img.save(bio, format='PNG')
    bio.seek(0)
    return ImageReader(bio)

def draw_multiline_text(c, text, x, y, max_width, line_height=14, font_name="Helvetica", font_size=11):
    """Desenha texto quebrado por linhas no canvas"""
    c.setFont(font_name, font_size)
    lines = []
    words = text.split()
    line = ""
    for word in words:
        if c.stringWidth(line + word + " ", font_name, font_size) < max_width:
            line += word + " "
        else:
            lines.append(line)
            line = word + " "
    if line:
        lines.append(line)
    for i, line in enumerate(lines):
        c.drawString(x, y - i*line_height, line)

def generate_error_pdf(filename="Vilao_ERROR_Status_Assignment.pdf"):
    c = canvas.Canvas(filename, pagesize=A4)
    width, height = A4

    # Cores
    dark_bg = colors.HexColor("#121212")
    text_color = colors.whitesmoke
    accent_red = colors.HexColor("#FF5555")
    accent_green = colors.HexColor("#55FF55")

    # Fundo escuro
    c.setFillColor(dark_bg)
    c.rect(0, 0, width, height, fill=1)

    # --- Capa ---
    c.setFillColor(accent_red)
    c.setFont("Helvetica-Bold", 28)
    c.drawCentredString(width/2, height-4*cm, "🦹‍♂️ VILÃO: ERROR")

    c.setFillColor(text_color)
    c.setFont("Helvetica", 14)
    c.drawCentredString(width/2, height-5.5*cm, "Nome Real: Yuto Hoshikawa")
    c.drawCentredString(width/2, height-6.3*cm, "Quirk: Status Assignment")
    c.drawCentredString(width/2, height-7.1*cm, "Afiliação: Organização Underground")
    c.drawCentredString(width/2, height-7.9*cm, "Nível de Ameaça: S")

    c.setFont("Helvetica-Oblique", 10)
    c.drawCentredString(width/2, 1.5*cm, "Ficha criada por ChatGPT")

    c.showPage()

    # --- Descrição do Quirk ---
    c.setFillColor(dark_bg)
    c.rect(0, 0, width, height, fill=1)
    c.setFillColor(accent_red)
    c.setFont("Helvetica-Bold", 22)
    c.drawString(2*cm, height-3*cm, "🧬 Quirk: Status Assignment")

    c.setFillColor(text_color)
    desc = ("Status Assignment permite a ERROR aplicar códigos de status inspirados em respostas HTTP sobre alvos, "
            "afetando diretamente sua condição física, mental ou energética. Cada código carrega um efeito específico que "
            "reflete o comportamento desse status em sistemas digitais, mas traduzido em efeitos reais no mundo físico.\n\n"
            "ERROR pode aplicar até 5 status simultaneamente, um por alvo. Cada status tem duração variável de 1 segundo a 15 minutos.")

    draw_multiline_text(c, desc, 2*cm, height-4*cm, width-4*cm, line_height=16, font_size=12)

    c.showPage()

    # --- Lista de Status ---
    c.setFillColor(dark_bg)
    c.rect(0, 0, width, height, fill=1)
    c.setFillColor(accent_red)
    c.setFont("Helvetica-Bold", 22)
    c.drawString(2*cm, height-3*cm, "📡 Lista de Status e Efeitos")

    c.setFillColor(text_color)
    y = height - 4.5*cm
    c.setFont("Helvetica-Bold", 14)

    statuses = [
        ("100 Continue", "O próximo status aplicado ao mesmo alvo será 1.5x mais forte. Visual: Códigos verdes flutuam ao redor do alvo."),
        ("101 Switching Protocols", "Alvo alterna entre habilidades. Visual: Engrenagens digitais girando."),
        ("200 OK", "Cura leve e marcação. Visual: Números 200 verdes se arrastam."),
        ("201 Created", "Cria um clone temporário. Visual: Partículas se montando."),
        ("202 Accepted", "Ação atrasada. Visual: Relógio em contagem regressiva."),
        ("204 No Content", "Invisibilidade total. Visual: Corpo apaga em silêncio."),
        ("301 Moved Permanently", "Teleporte curto. Visual: Seta girando."),
        ("302 Found", "Distrações ilusórias. Visual: Setas piscando."),
        ("304 Not Modified", "Imunidade a novos status. Visual: Textura metálica."),
        ("400 Bad Request", "Falha na ação. Visual: Alerta vermelho piscando."),
        ("401 Unauthorized", "Bloqueia quirks. Visual: Cadeado digital."),
        ("403 Forbidden", "Impede ações. Visual: Correntes de dados."),
        ("404 Not Found", "Remove buffs, escudos, clones ou efeitos ativos no alvo (dispelling). Visual: Fragmentos do alvo como linhas corrompidas."),
        ("405 Method Not Allowed", "Impede ataques. Visual: Campo bloqueador."),
        ("406 Not Acceptable", "Cria barreira. Visual: Escudo hexagonal digital."),
        ("409 Conflict", "Paralisia parcial. Visual: Fagulhas em conflito."),
        ("422 Unprocessable Entity", "Impede cura. Visual: Dados corrompidos."),
        ("429 Too Many Requests", "Lentidão. Visual: Buffering flutuante."),
        ("500 Internal Server Error", "Colapso mental. Visual: Glitches cerebrais."),
        ("501 Not Implemented", "Cancela ação. Visual: Mensagem 501 flutuante."),
        ("502 Bad Gateway", "Descoordenação. Visual: Ruídos e distorções."),
        ("503 Service Unavailable", "Exaustão. Visual: Corpo colapsando."),
        ("504 Gateway Timeout", "Congela alvo. Visual: Cronômetro zera."),
    ]

    line_height = 18
    for code, desc in statuses:
        if y < 3*cm:
            c.showPage()
            c.setFillColor(dark_bg)
            c.rect(0, 0, width, height, fill=1)
            y = height - 3*cm
            c.setFillColor(text_color)
        c.setFont("Helvetica-Bold", 14)
        c.drawString(2*cm, y, code)
        y -= 1.3*line_height
        c.setFont("Helvetica", 11)
        draw_multiline_text(c, desc, 3*cm, y, width - 5*cm, line_height=line_height)
        y -= (line_height * (desc.count(' ')//10 + 3))

    c.showPage()

    # --- Uniforme ---
    c.setFillColor(dark_bg)
    c.rect(0, 0, width, height, fill=1)
    c.setFillColor(accent_red)
    c.setFont("Helvetica-Bold", 22)
    c.drawString(2*cm, height-3*cm, "🧥 Uniforme de ERROR")

    c.setFillColor(text_color)
    uniform_desc = (
        "O traje de ERROR mistura circuitos com tecnologia obscura.\n\n"
        "Máscara: Visor preto translúcido com números giratórios. Exibe erros (404, 503) em tempo real.\n"
        "Tronco: Túnica preta com costuras digitais em vermelho, reflexiva sob luz. Circuitos conectam ao pescoço.\n"
        "Luvas: Pretas, com sensores digitais ativadores de status.\n"
        "Calças: Tecidos resistentes com trilhos de condução elétrica nos lados. Compartimentos ocultos.\n"
        "Botas: Sola magnética com isolamento de choque. Sistema de absorção de impacto.\n\n"
        "ERROR parcialmente vestido: carrega a máscara na mão, visor projetando erros holográficos."
    )
    draw_multiline_text(c, uniform_desc, 2*cm, height-4.5*cm, width-4*cm, line_height=14, font_size=11)

    # Criar imagens simples para as partes do uniforme
    parts = [
        ("Máscara", (50, 50, 50)),
        ("Tronco", (80, 0, 0)),
        ("Luvas", (30, 30, 30)),
        ("Calças", (40, 40, 40)),
        ("Botas", (60, 60, 60)),
        ("Máscara na mão (parcial)", (90, 10, 10)),
    ]

    x_img = 2*cm
    y_img = 7*cm
    img_spacing = 6*cm

    for i, (part_name, color) in enumerate(parts):
        img = create_uniform_part_image(part_name, color)
        c.drawImage(img, x_img + i*img_spacing, y_img, width=4*cm, height=4*cm, mask='auto')

    c.showPage()

    # --- Página final: frase ---
    c.setFillColor(dark_bg)
    c.rect(0, 0, width, height, fill=1)
    c.setFillColor(accent_red)
    c.setFont("Helvetica-Bold", 28)
    c.drawCentredString(width/2, height/2, "“404. Você não está mais aqui... nem seu escudo, nem sua esperança.”")

    c.save()
    print(f"PDF '{filename}' gerado com sucesso.")

if __name__ == "__main__":
    generate_error_pdf()
*/
