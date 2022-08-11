import {Action} from './action'
export class Menu {
        menId: any;
        code: string;
        menNom: string;
        menPath: string;
        menIcone: string;
        sousMenus: Menu[];
        actions: Action[];
       /* public Menu(nom:string, pathroute:string, menId?:any, code?:string, menIcone?:string, sousMenus?:Menu[]){
               this.menId = menId;
               this.menNom = nom;
               this.menPath = pathroute;
}*/
}
