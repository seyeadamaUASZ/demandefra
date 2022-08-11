import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Profil } from '../models/profil';
import { Widget } from '../models/widget';
import { TemplateWidget } from '../models/templatewidget';

@Injectable({
    providedIn: 'root'
})

export class WidgetService {
    api = environment.apii;
    constructor(private http: HttpClient) { }

    listeWidget() {
        return this.http.get<any>(this.api + 'listwidget');
    }
    listeTemplateWidget() {
        return this.http.get<any>(this.api + 'listwidgettemplate');
    }
    widgetTemplatesAttr(id:any){
        return this.http.get<any>(this.api + 'attribuertemplwidget/widgettemplatesAttr/'+id);
    }
    widgetTemplatesNotAttr(id:any){
        return this.http.get<any>(this.api + 'attribuertemplwidget/widgettemplatesNotAttr/'+id);
    }
    
    allTemplateByWidget(widget: any) {
        return this.http.post<any>(this.api + 'attribuertemplwidget/listtemplatewidget',widget);
    }
    infoWidget(id: any) {
        return this.http.get<any>(this.api + 'widget', id);
    }

    allWidgetByProfil(profil: any) {
        return this.http.post<any>(this.api + 'listwidgetprofil',profil);
    }

    allWidgetbyprofilNoAttr(profil: any) {
        return this.http.post<any>(this.api + 'listwidgetprofilnoattr',profil);
    }

    
allocateTemplate(formData:FormData) { 
    return this.http.post<any>(this.api + 'attribuertemplwidget/updateattributionwidget', formData);
  }
    attribuerWidget(p:Profil, removed:any[], added:any[], updated:any[]) {    
        //let formData = new FormData();
        //formData.append("removed", removed);
        //formData.append("added", added);
        let body = { 
          "profil":p,
          "removed": removed,
          "added": added
        }
        let profil = {
          "proId":p.proId
        }
        let formData = new FormData();
        formData.append("profil", JSON.stringify(profil));
        formData.append("removed", JSON.stringify(removed));
        formData.append("added", JSON.stringify(added));
        formData.append("updated", JSON.stringify(updated));
        return this.http.post<any>(this.api + 'attribuer/allocatewidget', formData);
      }

      createWidget(widget: Widget) {
        return this.http.post<any>(this.api + 'widget', widget);

    }
    updateWidget(widget: Widget) {
        return this.http.post<any>(this.api + 'widget/update', widget);

    }
    updateTempWidget(templatewidget: TemplateWidget) {
        return this.http.post<any>(this.api + 'widgettemplate/update', templatewidget);

    }
    
    createTemplateWidget(templatewidget: TemplateWidget) {
        return this.http.post<any>(this.api + 'widgettemplate', templatewidget);

    }
    deleteTemplateWidget(templatewidget: TemplateWidget) {
        return this.http.post<any>(this.api + 'widgettemplate/delete', templatewidget);

    }
    attrTemplateWidget(formData:FormData) { 
        return this.http.post<any>(this.api + 'attribuertemplwidget/attributionwidget', formData);

    }

    deleteWidget(widget: Widget){
        return this.http.post<any>(this.api + 'widget/delete', widget);
    }
    /*UpdateAttrTemplateWidget(formData:FormData) { 
        return this.http.post<any>(this.api + 'attribuertemplwidget/updateattributionwidget', formData);

    }*/
    
}