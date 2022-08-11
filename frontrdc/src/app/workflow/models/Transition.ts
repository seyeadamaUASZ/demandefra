import { Task } from "./task";
import { Workflow } from "./workflow";

export class Transition{
    trnId:any;
    trnAction:String;
    trnTskActuel:any;
    trnTskSuiv:any;
    trnWkfId:Workflow

}