import { Widget } from "src/app/home/models/widget";
import { Profile } from "src/app/utilisateur/models/profile";
import { Workflow } from "./workflow";

export class Paramwidget { 
    nomparam: String
    wkfId: Workflow
    wdgId: Widget
    poOwner:Profile
}