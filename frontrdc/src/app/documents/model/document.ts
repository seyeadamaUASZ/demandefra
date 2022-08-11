import { User } from 'src/app/utilisateur/models/user';
import { TypeDocuments } from './typeDocuments';

//import{TypeDocuments} from './typeDocuments'
export class Document{
    dctId: number;
    dctTitre: string;
    dctAuteur: String;
    dctDate: String;
    typeDocuments:TypeDocuments;
    dctBlob: String;
    dctType:string;
    statut:boolean;
}
