package pkg.controllori;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pkg.db.DBHelper;
import pkg.oggetti.Messaggio;
import pkg.oggetti.Utente;

@Controller
public class DefaultController {

    @Autowired
    private DBHelper db;

    @RequestMapping(value = "/")
    public String index(ModelMap map) {
        return "redirect:/index"; //reindirizza alla Home Page
    }

    @RequestMapping(value = "/index")
    public String home(ModelMap map) {
        return "index";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public String logIn(HttpServletRequest request, ModelMap map) {
        System.out.println("login");
        Utente u = db.getUser(request.getParameter("email"), request.getParameter("password"));
        if (u == null) {
            return "signin";
        }
        map.addAttribute("nome", u.getNome());
        map.addAttribute("cognome", u.getCognome());
        map.addAttribute("dataNascita", u.getDataNascita());
        map.addAttribute("email", u.getEmail());
        map.addAttribute("password", u.getPassword());
        map.addAttribute("sesso", u.getSesso());
        return "personalArea";
    }

    @RequestMapping(value = "/signin")
    public String signIn(ModelMap map) {
        return "signin";
    }

    @RequestMapping(value = "/login")
    public String logIn(ModelMap map) {
        return "login";
    }

    @RequestMapping(value = "/messages")
    public String message(ModelMap map) {
        List<Utente> lst = db.getAllUsers();
        System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "messages";
    }

    @RequestMapping(value = "/provaa")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String prova( String mittente, String mittenteG, String destinatario, String destinatarioG) {
        System.out.println("andata   " + mittente + " " + destinatario);
        //List<Utente> lst = db.getAllUsers();
        List<Messaggio> msg = db.getConversazione(mittente, mittenteG, destinatario, destinatarioG);
        //System.out.println(lst.size());
       // map.addAttribute("listaMessaggi", msg);
        //map.addAttribute("listaUtenti", lst);
        JSONObject js = new JSONObject();
        try {
            for (int i = 0; i < msg.size(); i++) {
                System.out.println(msg.get(i).getMessaggio());
                js.put("mittente", msg.get(i).getMittente());
                js.put("destinatario", msg.get(i).getDestinatario());
                js.put("messaggio", msg.get(i).getMessaggio());
                js.put("dataORa", msg.get(i).getDataOra());
            }
        } catch (JSONException ex) {
            System.out.println("errore json");
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(js.toString());
        return js.toString();
    }

    @RequestMapping(value = "/doSignin", method = RequestMethod.GET)
    public String signIn(HttpServletRequest request, ModelMap map) {
        System.out.println("reg");
        Utente temp = new Utente(request.getParameter("email"), request.getParameter("password"),
                request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("indirizzo"),
                request.getParameter("sesso").charAt(0), request.getParameter("dataNascita"), null, request.getParameter("telefono"), (request.getParameter("privacy").equals("true") ? true : false), null);
        db.sigIn(temp);
        return "signin";
    }
}
