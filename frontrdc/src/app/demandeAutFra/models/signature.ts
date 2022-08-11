import { User } from "src/app/utilisateur/models/user";

export class Signature {
    id: number;
    idSignataire: number;
    cleSignataire: string;
    codePin: String;
    utilisateur: User;
}