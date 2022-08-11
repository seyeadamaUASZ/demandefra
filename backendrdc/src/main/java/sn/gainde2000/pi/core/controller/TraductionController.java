package sn.gainde2000.pi.core.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Langue;
import sn.gainde2000.pi.core.entity.Traduction;
import sn.gainde2000.pi.core.service.IlangueService;

/**
 * 
 * @author PBF
 *
 */

@RestController
@CrossOrigin("*")
@RequestMapping("traduction")
public class TraductionController {
	@Value("${app.folderi18n}")
	private String ROOT_PATH ;

	
	@Autowired
	IlangueService langueService;

	/*
	 * Recuperation des traductions d'une langue via key ex: fr,en etc
	 */
	@GetMapping("i18n/{langue}")
	public Object i18n(@PathVariable("langue") String langue) {
		JSONObject jsonObject = getJsonFromFile(langue);
		if (jsonObject == null)
			return getJsonFromFile("fr").toString();
		return jsonObject.toString();

	}

	/*
	 * Ajout d'une traduction
	 */
	@PostMapping("create")
	public ServeurResponse create(@RequestBody Traduction traduction) {
		String dirKeys[] = traduction.getReference().split("\\.");
		JSONObject jsonObject = getJsonFromFile(traduction.getLangue().getLngLangue());
		JSONObject js = null;
		boolean statut = false;
		if (jsonObject != null) {

			js = rJsonAddOrUpdate(jsonObject, dirKeys, traduction.getSelectedLangue());
			if (js != null)
				statut = setJsonOnFile(traduction.getLangue().getLngLangue(), jsonObject.toString());

			// do the same for others language

			ArrayList<Langue> list = (ArrayList<Langue>) langueService.getListLang();
			for (Langue l : list) {
				if (!l.getLngLangue().equals(traduction.getLangue().getLngLangue())) {
					JSONObject jsonO = getJsonFromFile(l.getLngLangue());
					JSONObject j = null;
					boolean stat = false;
					if (jsonO != null) {
						boolean isFr = traduction.getLangue().getLngLangue().equals("fr");
						j = rJsonAddOrUpdate(jsonO, dirKeys,
								isFr ? traduction.getSelectedLangue() : traduction.getDefaultLangue());
						if (j != null)
							statut = setJsonOnFile(l.getLngLangue(), jsonO.toString());
					}
				}
			}
		}

		return new ServeurResponse(statut, statut ? "configuration.traduction.notification.add.success" : "configuration.traduction.notification.add.failure", js,null);
	}

	/*
	 * Mise a jour d'une traduction
	 */
	@PostMapping("update")
	public ServeurResponse update(@RequestBody Traduction traduction) {
		String dirKeys[] = traduction.getReference().split("\\.");
		JSONObject jsonObject = getJsonFromFile(traduction.getLangue().getLngLangue());
		JSONObject js = null;
		boolean statut = false;
		if (jsonObject != null) {

			js = rJsonAddOrUpdate(jsonObject, dirKeys, traduction.getSelectedLangue());
			if (js != null)
				statut = setJsonOnFile(traduction.getLangue().getLngLangue(), jsonObject.toString());
		}

		return new ServeurResponse(statut, statut ? "configuration.traduction.notification.add.success" : "configuration.traduction.notification.add.failure", js, null);
	}

 
	@PostMapping("delete")
	public ServeurResponse delete(@RequestBody Traduction traduction) {
		JSONObject js = getJsonFromFile(traduction.getLangue().getLngLangue());
		boolean statut = false;
		if (js != null) {
			String[] keys = traduction.getReference().split("\\.");
			js = rJsonRemove(js, keys);
			statut = setJsonOnFile(traduction.getLangue().getLngLangue(), js.toString());

			// do the same for others language

			ArrayList<Langue> list = (ArrayList<Langue>) langueService.getListLang();
			for (Langue l : list) {

				if (!l.getLngLangue().equals(traduction.getLangue().getLngLangue())) {
					JSONObject js2 = getJsonFromFile(l.getLngLangue());
					String[] keys2 = traduction.getReference().split("\\.");
					js2 = rJsonRemove(js2, keys2);
					statut = setJsonOnFile(l.getLngLangue(), js2.toString());
				}
			}
		}
		return new ServeurResponse(statut, statut ? "configuration.traduction.notification.delete.success" : "configuration.traduction.notification.delete.failure", js, null);
	}

	/*
	 * recuperer la liste des traduction d'une langue
	 */
	@PostMapping("liste")
	public ServeurResponse read(@RequestBody Langue langue) {
		JSONObject jsonObjectDefault = getJsonFromFile("fr");
		ArrayList<Traduction> list = new ArrayList<>();
		StringBuffer jsonSelected;
		StringBuffer jsonDefault = new StringBuffer(makeLinearObject("", jsonObjectDefault));
		jsonDefault = new StringBuffer("{" + jsonDefault.toString().replaceAll("\r\n", ",") + "}");
		jsonDefault = jsonDefault.replace(jsonDefault.length() - 2, jsonDefault.length() - 1, "");

		if (!langue.getLngLangue().equals("fr")) {

			JSONObject jsonObject = getJsonFromFile(langue.getLngLangue());

			jsonSelected = new StringBuffer(makeLinearObject("", jsonObject));
			jsonSelected = new StringBuffer("{" + jsonSelected.toString().replaceAll("\r\n", ",") + "}");
			jsonSelected = jsonSelected.replace(jsonSelected.length() - 2, jsonSelected.length() - 1, "");
		} else {
			jsonSelected = jsonDefault;
		}

		try {
			JSONObject defaultJson = new JSONObject(jsonDefault.toString());
			JSONObject selectedJson = new JSONObject(jsonSelected.toString());

			for (String key : defaultJson.keySet()) {
				list.add(new Traduction(key, defaultJson.getString(key),
						selectedJson.has(key) ? selectedJson.getString(key) : "", langue));
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			return new ServeurResponse(false, "configuration.traduction.list.failure", null,null);

		}

		return new ServeurResponse(true,langue.getLngLangue(), list,null);

	}

	/*
	 * fonction recursive permettant d'ajouter ou de supprimer une traduction dans un fichier de traduction
	 */
	public JSONObject rJsonAddOrUpdate(JSONObject js, String[] keys, String value) {
		JSONObject jsend = new JSONObject();
		if (keys.length == 0)
			return js;
		if (keys.length > 1) {
			String k0 = keys[0];

			keys = (String[]) ArrayUtils.remove(keys, 0);
			if (js.has(k0)) {
				js.put(k0, rJsonAddOrUpdate((JSONObject) js.get(k0), keys, value));
			} else {
				js.put(k0, rJsonAddOrUpdate(jsend, keys, value));
			}
		} else if (keys.length == 1) {
			return js.put(keys[0], value);
		}

		return js;

	}

	/*
	 * Fonction recursive permettant la suppression d'une traduction
	 */
	public JSONObject rJsonRemove(JSONObject js, String[] keys) {

		if (keys.length == 0)
			return js;
		if (keys.length > 1) {
			String k0 = keys[0];

			keys = (String[]) ArrayUtils.remove(keys, 0);
			if (js.has(k0)) {
				js.put(k0, rJsonRemove((JSONObject) js.get(k0), keys));
			} else {

				return js;
			}
		} else if (keys.length == 1) {

			if (js.has(keys[0])) {
				js.remove(keys[0]);
				return js;
			} else {

				return js;
			}
		}
		return js;
	}

	/*
	 * Fonction recursive permettant de deconstruire un objet json en une liste de cle et valeur 
	 */
	public String makeLinearObject(String k0, JSONObject jsonObject) {

		String keys[] = JSONObject.getNames(jsonObject);
		StringBuffer s = new StringBuffer();
		if (keys == null)
			return "";
		if (keys.length == 0)
			return s.toString();
		for (String k : keys) {

			if (jsonObject.get(k) instanceof JSONObject) {
				if (!k0.trim().isEmpty() && k0.lastIndexOf(".") != (k0.length() - 1))
					k0 += ".";
				s.append(makeLinearObject(k0 + k, (JSONObject) jsonObject.get(k)));
			} else {

				if (!k0.isEmpty()) {
					if (!k0.trim().isEmpty() && k0.lastIndexOf(".") != (k0.length() - 1))
						k0 += ".";
					s.append(JSONObject.quote(k0 + k) + ":" + JSONObject.quote(jsonObject.get(k).toString()) + "\r\n");
				} else {
					s.append(JSONObject.quote(k) + ":" + JSONObject.quote(jsonObject.get(k).toString()) + "\r\n");

				}
			}

		}

		return s.toString();

	}

	/*
	 * Fonction permettant de recuperer le contenu d'un fichier json
	 */
	public JSONObject getJsonFromFile(String langue) {

		Path dir = Paths.get(ROOT_PATH);

		if (!Files.exists(dir)) {
			try {
				Files.createDirectories(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("templates/assets/i18n/" + "fr" + ".json").getFile());
		File f = new File(ROOT_PATH + "/" + langue + ".json");
		File fDft = new File(ROOT_PATH + "/fr.json");
		if (!f.exists()) {
			try {
				if (fDft.exists())
					Files.copy(fDft.toPath(), Paths.get(ROOT_PATH + "/" + langue + ".json"));
				else
					Files.copy(file.toPath(), Paths.get(ROOT_PATH + "/" + langue + ".json"));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		BufferedReader buffer;
		StringBuffer jsonbuffer = new StringBuffer();

		try {
			buffer = Files.newBufferedReader(Paths.get(ROOT_PATH + "/" + langue + ".json"), Charset.forName("UTF-8"));
			String line = null;
			while ((line = buffer.readLine()) != null) {
				jsonbuffer.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (jsonbuffer.toString().isEmpty()) {
				return null;
			} else {
				try {
					JSONObject j = new JSONObject(jsonbuffer.toString());
					return j;
				} catch (JSONException ex) {
					ex.printStackTrace();
					return null;
				}

			}
		}

	}

	/*
	 * Fonction permettant de sauvegarder un objet json sur un fichier
	 */
	public boolean setJsonOnFile(String langue, String json) {
		Path path = Paths.get(ROOT_PATH);
		File file = new File(path.toString() + "/" + langue + ".json");
		try {

			// general writer
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos, Charset.forName("UTF-8")));
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			return true;
		}
	}

}
