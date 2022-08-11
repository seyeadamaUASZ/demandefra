import { Profile } from "./profile";
import { Langue } from './langue';
import { Theme } from './theme';

export class User {
    utiId;
    utiUsername: string;
    utiAdresse: string;
    utiCouleur: string;
    utiDateCreation: string;
    utiDateModification: string;
    utiEmail: string;
    utiLangue: string;
    utiNom: string;
    utiPassword: string;
    utiPrenom: string;
    utiTelephone: string;
    utiLogo: any;
    utiTheme: any;
    utiAppId: any;
    uti_actived: any;
    uti_pro_id: Profile;
    uti_lng_id: Langue;
    uti_thm_id: Theme;
    idEmpriente:string;
    

}
