package GabrielPavan.com.GitHub.CM.visao;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import GabrielPavan.com.GitHub.CM.modelo.Campo;
import GabrielPavan.com.GitHub.CM.modelo.CampoEvento;
import GabrielPavan.com.GitHub.CM.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton 
	implements CampoObservador, MouseListener {
	
	private final Color BG_PADRAO = new Color(171,171,171);
	private final Color BG_MARCADO = new Color(8,179,247);
	private final Color BG_EXPLOSAO = new Color(189,66,68);
	private final Color TEXTO_VERDE = new Color(0,100,0);
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		addMouseListener(this);
		campo.registrarObservador(this);
	}
	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
			case ABRIR:
				aplicarEstiloABRIR();
				break;
			case MARCAR:
				aplicarEstiloMARCAR();
				break;
			case EXPLODIR:
				aplicarEstiloEXPLODIR();
				break;
			default:
				aplicarEstiloPADRAO();
		}
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}
	private void aplicarEstiloPADRAO() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}
	private void aplicarEstiloEXPLODIR() {
		setBackground(BG_EXPLOSAO);
		setText("X");
	}
	private void aplicarEstiloMARCAR() {
		setBackground(BG_MARCADO);
		setText("M");
	}
	private void aplicarEstiloABRIR() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLOSAO);
			return;
		}
		setBackground(BG_PADRAO);
		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(Color.BLUE);
			break;
		case 2:
			setForeground(TEXTO_VERDE);
			break;
		case 3:
			setForeground(Color.RED);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.ORANGE);
			break;
		default:
			setForeground(Color.PINK);
		}
		String valor = !campo.vizinhacaSegura() ? campo.minasNaVizinhanca()+"" : "";
		setText(valor);
	}
	//Inteface dos eventos do mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
