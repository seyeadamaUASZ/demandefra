import { Injectable, NgZone } from "@angular/core";
import { MatSnackBar, MatSnackBarConfig } from "@angular/material/snack-bar";

@Injectable({ providedIn: "root" })
export class NotificationService {
  constructor(
    private snackBar: MatSnackBar/*,
    private readonly zone: NgZone*/
  ) {}
   playAudio(){
      let audio = new Audio();
      audio.src = "../../../assets/audio/sound.mp3";
      audio.load();
      audio.play();
    }
    

  default(message: string, isHandset?: boolean) {
    this.show(
      message,
      {
        duration: 20000,
        panelClass: "default-notification-overlay",
      },
      isHandset
    );
  }

  info(message: string, isHandset?: boolean) {
    this.show(
      message,
      {
        duration: 10000,
        panelClass: "info-notification-overlay",
      },
      isHandset
    );
  }

  success(message: string, isHandset?: boolean) {
    this.show(
      message,
      {
        duration: 10000,
        panelClass: "success-notification-overlay",
      },
      isHandset
    );
  }

  warn(message: string, isHandset?: boolean) {
    this.show(
      message,
      {
        duration: 20000,
        panelClass: "warning-notification-overlay",
      },
      isHandset
    );
  }

  error(message: string, isHandset?: boolean) {
    this.show(
      message,
      {
        duration: 10000,
        panelClass: "error-notification-overlay",
      },
      isHandset
    );
  }

  private show(message: string,  configuration: MatSnackBarConfig, isHandset?: boolean) {    
    // If desktop, move it to top-right
    if (!isHandset) {
      configuration.horizontalPosition = "right";
      configuration.verticalPosition = "bottom";
    }

    this.snackBar.open(message, null, configuration);
    // Need to open snackBar from Angular zone to prevent issues with its position per   
    //this.zone.run(() => this.snackBar.open(message, null, configuration));
  }
}