export interface NavItem {
    menNom: string;
    menIconeColor?: string;
    menIcone: string;
    menPath?: string;
    sousMenus?: NavItem[];
  }