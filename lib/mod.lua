local fx = require("fx/lib/fx")
local mod = require 'core/mods'

local FxDistortion = fx:new{
    subpath = "/fx_distortion"
}


function FxDistortion:add_params()
    params:add_separator("fx_distortion", "fx distortion")
    FxDistortion:add_slot("fx_distortioon_slot", "slot")
    FxDistortion:add_control("fx_distortion_drive", "drive", "drive", controlspec.new(0, 1, 'lin', 0, 0.5))
    FxDistortion:add_control("fx_distortion_tone", "tone", "tone", controlspec.new(0, 1, 'lin', 0, 0.5))
    FxDistortion:add_control("fx_distortion_resonance", "res", "res", controlspec.new(0, 1, 'lin', 0, 0.1))
    FxDistortion:add_control("fx_distortion_noise", "noise", "noise", controlspec.new(0, 1, 'lin', 0, 0.003))
end

mod.hook.register("script_pre_init", "distortion mod pre init", function()
    FxDistortion:install()
end)

mod.hook.register("script_post_cleanup", "distortion mod post cleanup", function()
end)

return FxDistortion