import { Langue } from './langue';
import { Theme } from './theme';
import { Image } from './image';

export class Parametre {
    param_id: number;
    paramUtiUsername: string;
    param_lng_id: Langue;
    param_thm_id: Theme;
    param_img_id: Image;
}