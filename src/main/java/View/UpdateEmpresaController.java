package View;

import static Config.Config.ALTERAR;
import static Config.Config.INCLUIR;
import static Config.DAO.cidadeRepository;
import static Config.DAO.empresaRepository;
import Model.Cidade;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.swing.text.MaskFormatter;
import org.springframework.data.domain.Sort;

public class UpdateEmpresaController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    EmpresaController controllerPai;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtRazaoSocial;
    @FXML
    private TextField txtFantasia;
    @FXML
    private TextField txtObs;
    @FXML
    private ComboBox<Cidade> cbCidade;
    @FXML
    private JFXButton btnConfirma;
    @FXML
    private JFXButton btnIncluirCidade;
    @FXML
    private JFXButton btnEditarCidade;
    @FXML
    private VBox vbPrincipal;
    @FXML
    private StackPane pnCidade;
    @FXML
    private Label lbIncluiAltera;
    @FXML
    private TextField txtNomeCidade;
    @FXML
    private TextField txtSiglaCidade;
    @FXML
    private JFXButton btnConfirmaCidade;

    char acao;
    Cidade cidade;

    @FXML
    private void acEditarCidadeClick() {
        vbPrincipal.setVisible(false);
        pnCidade.setVisible(true);
        lbIncluiAltera.setText("Editar Cidade");
        acao = ALTERAR;
        cidade = cbCidade.getSelectionModel().getSelectedItem();
        txtNomeCidade.setText(cbCidade.getSelectionModel().getSelectedItem().getNome());
        txtSiglaCidade.setText(cbCidade.getSelectionModel().getSelectedItem().getSigla());
    }

    @FXML
    private void acIncluirCidadeClick() {
        vbPrincipal.setVisible(false);
        pnCidade.setVisible(true);
        lbIncluiAltera.setText("Incluir Cidade");
        acao = INCLUIR;
        txtNomeCidade.setText("");
        txtSiglaCidade.setText("");
        cidade = new Cidade();
    }

    @FXML
    private void acConfirmaCidade() {

        cidade.setNome(txtNomeCidade.getText());
        cidade.setSigla(txtSiglaCidade.getText());
        try {
            switch (acao) {
                case INCLUIR:
                    cidadeRepository.insert(cidade);
                    acVoltaClick();
                    break;
                case ALTERAR:
                    cidadeRepository.save(cidade);
                    cbCidade.setItems(FXCollections.observableList(
                            cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
                    cbCidade.getSelectionModel().select(controllerPai.empresa.getCidade());
                    acVoltaClick();
                    break;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cidade: " + cbCidade.getSelectionModel().getSelectedItem().getNome());

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Cidade já cadastrada");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            acVoltaClick();
        }
        cbCidade.setItems(FXCollections.observableList(
                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
        cbCidade.getSelectionModel().select(controllerPai.empresa.getCidade());
    }

    @FXML
    private void acVoltaClick() {
        vbPrincipal.setVisible(true);
        pnCidade.setVisible(false);
    }

    @FXML
    private void acConfirmaClick() {
        
        if (txtCnpj.getText().length() < 18 || !validaCNPJ(retiraMascara(txtCnpj))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("CNPJ inválido!!");
            alert.setContentText("CNPJ inválido!!");
            alert.showAndWait();
            txtCnpj.setText("");
            txtCnpj.requestFocus();
        } else {
            controllerPai.empresa.setCnpj(retiraMascara(txtCnpj));
            controllerPai.empresa.setRazaoSocial(txtRazaoSocial.getText());
            controllerPai.empresa.setFantasia(txtFantasia.getText());
            controllerPai.empresa.setObservacao(txtObs.getText());
            controllerPai.empresa.setCidade(cbCidade.getSelectionModel().getSelectedItem());
            anchorPane.getScene().getWindow().hide();
        }
        try {
            empresaRepository.save(controllerPai.empresa);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Edição de empresa");

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("CNPJ já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            anchorPane.getScene().getWindow().hide();
        }
        controllerPai.tbVwEmpresa.setItems(FXCollections.observableList(empresaRepository.findAll()));
    }

    public void setUpdateController(EmpresaController controllerPai) {
        
        this.controllerPai = controllerPai;
        txtCnpj.setText(controllerPai.empresa.getCnpjTb());
        txtRazaoSocial.setText(controllerPai.empresa.getRazaoSocial());
        txtFantasia.setText(controllerPai.empresa.getFantasia());
        txtObs.setText(controllerPai.empresa.getObservacao());
        cbCidade.setItems(FXCollections.observableList(
                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
        cbCidade.getSelectionModel().select(controllerPai.empresa.getCidade());

    }

    private boolean validaCNPJ(String cnpj) {
        int multiplicador11 = 5, multiplicador12 = 9;
        int multiplicador21 = 6, multiplicador22 = 9;
        int soma1 = 0, soma2 = 0, num;
        if (cnpj.equals("00000000000000")
                || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555557775555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999")
                || cnpj.length() != 14) {
            return false;
        }
        for (int i = 0; i <= 3; i++) {
            num = (int) (cnpj.charAt(i) - 48);
            soma1 = soma1 + (num * multiplicador11);
            multiplicador11--;
        }
        for (int i = 4; i <= 11; i++) {
            num = (int) (cnpj.charAt(i) - 48);
            soma1 = soma1 + (num * multiplicador12);
            multiplicador12--;
        }
        for (int i = 0; i <= 4; i++) {
            num = (int) (cnpj.charAt(i) - 48);
            soma2 = soma2 + (num * multiplicador21);
            multiplicador21--;
        }
        for (int i = 5; i <= 12; i++) {
            num = (int) (cnpj.charAt(i) - 48);
            soma2 = soma2 + (num * multiplicador22);
            multiplicador22--;
        }
        int auxVerif1 = 11 - (soma1 % 11);
        if (auxVerif1 >= 10) {
            auxVerif1 = 0;
        }
        int auxVerif2 = 11 - (soma2 % 11);
        if (auxVerif2 >= 10) {
            auxVerif2 = 0;
        }
        int verif1 = (int) (cnpj.charAt(12) - 48);
        int verif2 = (int) (cnpj.charAt(13) - 48);
        if (verif1 != auxVerif1 || verif2 != auxVerif2) {
            return false;
        } else {
            return true;
        }
    }

    public void formatterCNPJ(TextField t) {
        t.textProperty().addListener((ObservableValue<? extends String> ov, String antigo, String novo) -> {
            validaCNPJ(t.getText());
            if (!novo.isEmpty() && novo.length() > antigo.length()) {
                try {

                    switch (novo.length()) {
                        case 2:
                            t.setText(novo + ".");
                            Platform.runLater(() -> {
                                t.end();
                            });
                            break;
                        case 6:
                            t.setText(novo + ".");
                            Platform.runLater(() -> {
                                t.end();
                            });
                            break;
                        case 10:
                            t.setText(novo + "/");
                            Platform.runLater(() -> {
                                t.end();
                            });
                            break;
                        case 15:
                            t.setText(novo + "-");
                            break;
                        case 19:
                            t.setText(antigo);
                            break;
                    }
                } catch (NumberFormatException e) {

                }
            }
        });
    }
    
    private String retiraMascara(TextField t){
        String[] parte = t.getText().split("");
        return parte[0]+parte[1]+
                parte[3]+parte[4]+parte[5]+
                parte[7]+parte[8]+parte[9]+
                parte[11]+parte[12]+parte[13]+parte[14]+
                parte[16]+parte[17];
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatterCNPJ(txtCnpj);
        btnConfirma.disableProperty().bind(txtCnpj.textProperty().isEmpty().or(txtFantasia.textProperty().isEmpty()).or(txtRazaoSocial.textProperty().isEmpty()));
        btnConfirmaCidade.disableProperty().bind(txtNomeCidade.textProperty().isEmpty().or(txtSiglaCidade.textProperty().isEmpty()));
    }
}
