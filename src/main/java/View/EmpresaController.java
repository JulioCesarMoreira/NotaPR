package View;

import Config.DAO;
import static Config.DAO.cidadeRepository;
import static Config.DAO.empresaRepository;
import Model.Empresa;
import Model.Cidade;
import Utility.XPopOver;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import org.springframework.data.domain.Sort;

public class EmpresaController implements Initializable {

    @FXML
    public TableView<Empresa> tbVwEmpresa;

    @FXML
    private ComboBox<Cidade> cbCidade;

    @FXML
    private JFXTextField txtFldBusca;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private void tblVwClick() {

        empresa = tbVwEmpresa.getSelectionModel().getSelectedItem();
        showUpdate();
        System.out.println(empresa.getObservacao());

    }

    @FXML
    private void acBusca(KeyEvent e) {
        if (!txtFldBusca.getText().isEmpty() && e.getCode() == KeyCode.ENTER) {
            lstEmpresa = empresaRepository.findByCnpjLikeOrFantasiaLikeIgnoreCaseOrRazaoSocialLikeIgnoreCase(
                    txtFldBusca.getText(), txtFldBusca.getText(), txtFldBusca.getText());
        }
    }

    @FXML
    private void acLimpaBusca() {
        tbVwEmpresa.setItems(FXCollections.observableList(empresaRepository.findAll(new Sort(new Sort.Order("fantasia")))));
        txtFldBusca.setText("");
    }

    private void showUpdate() {
        String cena = "/fxml/UpdateEmpresa.fxml";
        XPopOver popOver = null;

        popOver = new XPopOver(cena, "Atualização de empresa", null);

        UpdateEmpresaController controllerFilho
                = popOver.getLoader().getController();
        controllerFilho.setUpdateController(this);
    }

    Cidade cidade;
    Empresa empresa;
    List<Empresa> lstEmpresa = new ArrayList<>();
    List<Cidade> lstCidade = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        cidade = new Cidade("Ponta Grosa", "PGR");
//        empresa = new Empresa("0001", "14537140000108", "Dp Moreira Construtora", "Dp Moreira Construtora", "Contrutora de Obras", cidade);
//        lstEmpresa.add(empresa);
//        tbVwEmpresa.setItems(FXCollections.observableList(lstEmpresa));
//        empresaRepository.insert(new Empresa("001", "14537140000108", "DP moreira", "DP moreira", "Contrutora de Obras", cidadeRepository.findBySigla("PGR")));
//        empresaRepository.insert(new Empresa("002", "16464708000133", "Varanda Grill", "Varanda Grill", "Restaurante gostoso", cidadeRepository.findBySigla("PGR")));
//        empresaRepository.insert(new Empresa("003", "01506321000125", "JAMIL DUTRA DE SOUZA E CIA LTDA ", "JAMIL DUTRA DE SOUZA E CIA LTDA", "empresa Jamil", cidadeRepository.findBySigla("GTB")));
//        empresaRepository.insert(new Empresa("004", "22034988000142", "PONTA GROSSA POINT COMERCIO DE ALIMENTOS LTDA EPP", "PONTA GROSSA POINT COMERCIO DE ALIMENTOS LTDA EPP", "Point foods", cidadeRepository.findBySigla("IPI")));
//        empresaRepository.insert(new Empresa("005", "02727390000636", "L Fiorotto Com Alim Eireli -Aero3", "L Fiorotto Com Alim Eireli -Aero3", "Fiorotto Filial 03", cidadeRepository.findBySigla("CRB")));
//        empresaRepository.insert(new Empresa("006", "80218076000127", "HOTEL PLANALTO PALACE LTDA", "HOTEL PLANALTO PALACE LTDA", "Hotel alto padrão", cidadeRepository.findBySigla("CAT")));
//        empresaRepository.insert(new Empresa("007", "16803469000603", "SMART CATERING SERV. SOL. ALIM. LTDA", "SMART CATERING SERV. SOL. ALIM. LTDA", "Sistema de marmitas", cidadeRepository.findBySigla("CTR")));
//        cidadeRepository.insert(new Cidade("Castro","CTR"));
//        cidadeRepository.insert(new Cidade("Ponta Grossa", "PGR"));
//        cidadeRepository.insert(new Cidade("Carambéi","CRB"));
//        cidadeRepository.insert(new Cidade("Ipiranga","IPI"));
//        cidadeRepository.insert(new Cidade("Guaratuba","GTB"));
//        cidadeRepository.insert(new Cidade("Catandúvas","CAT"));
        tbVwEmpresa.setItems(FXCollections.observableList(empresaRepository.findAll(new Sort(new Sort.Order("fantasia")))));
        btnEditar.disableProperty().bind(Bindings.isEmpty((tbVwEmpresa.getSelectionModel().getSelectedItems())));
    }
}
