FxDistortion : FxBase {

    *new { 
        var ret = super.newCopyArgs(nil, \none, (
            drive: 0.5,  // Default value for drive
            tone: 0.5,   // Default value for tone
            res: 0.1,    // Default value for filter resonance
            noise: 0.0003  // Default value for noise level
        ), nil, 2);  // 2-channel stereo processing
        ^ret;
    }

    *initClass {
        FxSetup.register(this.new);
    }

    subPath {
        ^"/fx_distortion";
    }  

    symbol {
        ^\fxDistortion;
    }

    addSynthdefs {
        SynthDef(\fxDistortion, {|inBus, outBus, drive=0.5, tone=0.5, res=0.1, noise=0.0003|
            var input = In.ar(inBus, 2);
            var wet, freq, filterType, processed;

            wet = HPF.ar(input, 25);
            wet = (wet * LinExp.kr(drive, 0, 1, 1, 5)).distort;

            freq = Select.kr(tone > 0.75, [
              Select.kr(tone > 0.2, [
                LinExp.kr(tone, 0, 0.2, 10, 400),
                LinExp.kr(tone, 0.2, 0.75, 400, 20000)
              ]),
              LinExp.kr(tone, 0.75, 1, 20, 21000)
            ]);
            filterType = Select.kr(tone > 0.75, [0, 1]);
            wet = DFM1.ar(
              wet,
              freq,
              res,
              1.0,
              filterType,
              noise
            ).softclip;

            Out.ar(outBus, wet);
        }).add;
    }

    // Add any other necessary methods or overrides here...

}