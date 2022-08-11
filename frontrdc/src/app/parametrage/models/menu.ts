import { Action } from './Action';
export class Menu {
        menId: string;
        menIdParent: string;
        menNom: string;
        menPath: string;
        menIcone: string;
        menIconeColor: string;
        sousMenus:Menu[];
        actions:Action[];
}
