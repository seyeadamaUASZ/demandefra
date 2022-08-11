import { Profil } from "./profil";
import { Langue } from './langue';
import { Theme } from './theme';

export class User {
    utiId;
    utiUsername: string;
    utiAdresse: String;
    utiCouleur: String;
    utiDateCreation: String;
    utiDateModification: String;
    utiEmail: String;
    utiLangue: String;
    utiNom: String;
    utiPassword: String;
    utiPrenom: String;
    utiTelephone: String;
    utiTheme: any;
    utiAppId: any;
    uti_actived: any;
    uti_pro_id: Profil;
    uti_lng_id: Langue;
    uti_thm_id: Theme;

}
