import { Menu } from './menu';

export class Action {
        actId: string;
        actMenId: Menu;
        actNom: string;
        actCode: string;
        actDescription: string;

        public setActMenId(menId){                
                this.actMenId = new Menu();
                 this.actMenId.menId = menId;
        }
}